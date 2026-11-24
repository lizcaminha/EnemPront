package com.info3c.enemPront.service;

import com.info3c.enemPront.dto.Desempenho;
import com.info3c.enemPront.dto.LinhaDesempenho;
import com.info3c.enemPront.dto.PerfilView;
import com.info3c.enemPront.model.Usuario;
import com.info3c.enemPront.repository.QuestaoTopicoRepository;
import com.info3c.enemPront.repository.TentativaRespostaRepository;
import com.info3c.enemPront.util.Ofensiva;
import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DesempenhoService {

    private static final int DIAS_META_OFENSIVA = 7;
    private static final int LIMITE_TOPICOS_FRACOS = 5;

    private final TentativaRespostaRepository tentativas;
    private final QuestaoTopicoRepository questaoTopicos;
    private final Clock clock;

    public DesempenhoService(TentativaRespostaRepository tentativas, QuestaoTopicoRepository questaoTopicos,
            Clock clock) {
        this.tentativas = tentativas;
        this.questaoTopicos = questaoTopicos;
        this.clock = clock;
    }

    @Transactional(readOnly = true)
    public Desempenho calcular(Long usuarioId) {
        long total = tentativas.contarPorUsuario(usuarioId);
        long acertos = tentativas.contarAcertosPorUsuario(usuarioId);

        Map<LocalDate, Long> porDia = new HashMap<>();
        for (Object[] linha : tentativas.contarPorDia(usuarioId)) {
            porDia.put((LocalDate) linha[0], ((Number) linha[1]).longValue());
        }
        Ofensiva.Resultado ofensiva = Ofensiva.calcular(porDia, LocalDate.now(clock));

        List<Object[]> resumoPorQuestao = tentativas.resumoPorQuestao(usuarioId);
        List<LinhaDesempenho> porArea = new ArrayList<>();
        List<LinhaDesempenho> topicosFracos = new ArrayList<>();
        agruparPorAreaETopico(resumoPorQuestao, porArea, topicosFracos);

        return new Desempenho(
                total,
                acertos,
                total - acertos,
                percentual(acertos, total),
                ofensiva.diasSeguidos(),
                ofensiva.maiorOfensiva(),
                ofensiva.questoesHoje(),
                ofensiva.recordeDiario(),
                Math.min(ofensiva.diasSeguidos(), DIAS_META_OFENSIVA),
                porArea,
                topicosFracos);
    }

    @Transactional(readOnly = true)
    public PerfilView perfil(Usuario usuario) {
        long total = tentativas.contarPorUsuario(usuario.getId());
        long acertos = tentativas.contarAcertosPorUsuario(usuario.getId());
        Map<LocalDate, Long> porDia = new HashMap<>();
        for (Object[] linha : tentativas.contarPorDia(usuario.getId())) {
            porDia.put((LocalDate) linha[0], ((Number) linha[1]).longValue());
        }
        Ofensiva.Resultado ofensiva = Ofensiva.calcular(porDia, LocalDate.now(clock));
        return new PerfilView(usuario.getNome(), usuario.getEmail(), total, ofensiva.diasSeguidos(),
                percentual(acertos, total));
    }

    private void agruparPorAreaETopico(List<Object[]> resumoPorQuestao, List<LinhaDesempenho> porArea,
            List<LinhaDesempenho> topicosFracos) {
        if (resumoPorQuestao.isEmpty()) {
            return;
        }
        Map<Long, long[]> desempenhoPorQuestao = new HashMap<>();
        for (Object[] linha : resumoPorQuestao) {
            long totalQuestao = ((Number) linha[1]).longValue();
            long acertosQuestao = linha[2] == null ? 0 : ((Number) linha[2]).longValue();
            desempenhoPorQuestao.put((Long) linha[0], new long[] {totalQuestao, acertosQuestao});
        }

        Map<Long, String> nomesAreas = new HashMap<>();
        Map<Long, String> nomesTopicos = new HashMap<>();
        Map<Long, Set<Long>> areasDaQuestao = new HashMap<>();
        Map<Long, Set<Long>> topicosDaQuestao = new HashMap<>();
        for (Object[] linha : questaoTopicos.topicosDasQuestoes(desempenhoPorQuestao.keySet())) {
            Long questaoId = (Long) linha[0];
            Long topicoId = (Long) linha[1];
            Long areaId = (Long) linha[3];
            nomesTopicos.put(topicoId, (String) linha[2]);
            nomesAreas.put(areaId, (String) linha[4]);
            topicosDaQuestao.computeIfAbsent(questaoId, chave -> new HashSet<>()).add(topicoId);
            areasDaQuestao.computeIfAbsent(questaoId, chave -> new HashSet<>()).add(areaId);
        }

        Map<Long, long[]> totaisPorArea = somar(desempenhoPorQuestao, areasDaQuestao);
        Map<Long, long[]> totaisPorTopico = somar(desempenhoPorQuestao, topicosDaQuestao);

        for (Map.Entry<Long, long[]> entrada : totaisPorArea.entrySet()) {
            porArea.add(linha(nomesAreas.get(entrada.getKey()), entrada.getValue()));
        }
        porArea.sort(Comparator.comparing(LinhaDesempenho::nome));

        List<LinhaDesempenho> todosOsTopicos = new ArrayList<>();
        for (Map.Entry<Long, long[]> entrada : totaisPorTopico.entrySet()) {
            todosOsTopicos.add(linha(nomesTopicos.get(entrada.getKey()), entrada.getValue()));
        }
        todosOsTopicos.stream()
                .filter(l -> l.erros() > 0)
                .sorted(Comparator.comparingInt(LinhaDesempenho::taxa)
                        .thenComparing(Comparator.comparingLong(LinhaDesempenho::total).reversed()))
                .limit(LIMITE_TOPICOS_FRACOS)
                .forEach(topicosFracos::add);
    }

    private static Map<Long, long[]> somar(Map<Long, long[]> desempenhoPorQuestao, Map<Long, Set<Long>> grupos) {
        Map<Long, long[]> totais = new HashMap<>();
        for (Map.Entry<Long, long[]> questao : desempenhoPorQuestao.entrySet()) {
            for (Long grupo : grupos.getOrDefault(questao.getKey(), Set.of())) {
                long[] acumulado = totais.computeIfAbsent(grupo, chave -> new long[2]);
                acumulado[0] += questao.getValue()[0];
                acumulado[1] += questao.getValue()[1];
            }
        }
        return totais;
    }

    private static LinhaDesempenho linha(String nome, long[] totais) {
        return new LinhaDesempenho(nome, totais[0], totais[1], totais[0] - totais[1], percentual(totais[1], totais[0]));
    }

    private static int percentual(long parte, long total) {
        return total == 0 ? 0 : (int) Math.round(parte * 100.0 / total);
    }
}
