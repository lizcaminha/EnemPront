package com.info3c.enemPront.service;

import com.info3c.enemPront.dto.ProvaView;
import com.info3c.enemPront.dto.QuestaoView;
import com.info3c.enemPront.dto.ResultadoSimulado;
import com.info3c.enemPront.dto.SimuladoSessao;
import com.info3c.enemPront.exception.RecursoNaoEncontradoException;
import com.info3c.enemPront.exception.RegraNegocioException;
import com.info3c.enemPront.model.Questao;
import com.info3c.enemPront.model.TentativaResposta;
import com.info3c.enemPront.model.Usuario;
import com.info3c.enemPront.repository.QuestaoRepository;
import com.info3c.enemPront.repository.TentativaRespostaRepository;
import com.info3c.enemPront.repository.UsuarioRepository;
import com.info3c.enemPront.util.Textos;
import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimuladoService {

    public static final int MINUTOS_POR_QUESTAO = 3;

    private static final Set<Integer> QUANTIDADES = Set.of(5, 10, 20, 45);

    private final QuestaoRepository questoes;
    private final TentativaRespostaRepository tentativas;
    private final UsuarioRepository usuarios;
    private final QuestaoService questaoService;
    private final Clock clock;

    public SimuladoService(QuestaoRepository questoes, TentativaRespostaRepository tentativas,
            UsuarioRepository usuarios, QuestaoService questaoService, Clock clock) {
        this.questoes = questoes;
        this.tentativas = tentativas;
        this.usuarios = usuarios;
        this.questaoService = questaoService;
        this.clock = clock;
    }

    @Transactional(readOnly = true)
    public SimuladoSessao iniciar(Integer quantidade, Long areaId) {
        if (quantidade == null || !QUANTIDADES.contains(quantidade)) {
            throw new RegraNegocioException("Escolha a quantidade de questões");
        }
        List<Long> ids = new ArrayList<>(areaId == null ? questoes.findAllIds() : questoes.findIdsByArea(areaId));
        if (ids.isEmpty()) {
            throw new RegraNegocioException("Ainda não há questões cadastradas para essa seleção");
        }
        Collections.shuffle(ids);
        List<Long> escolhidas = List.copyOf(ids.subList(0, Math.min(quantidade, ids.size())));
        return new SimuladoSessao(escolhidas, clock.millis(), escolhidas.size() * MINUTOS_POR_QUESTAO);
    }

    @Transactional(readOnly = true)
    public ProvaView prova(SimuladoSessao sessao) {
        List<QuestaoView> views = questaoService.carregar(sessao.ids(), Map.of(), false);
        long restante = Math.max(0, (sessao.fimMillis() - clock.millis()) / 1000);
        return new ProvaView(views, restante, views.size());
    }

    @Transactional
    public ResultadoSimulado finalizar(Long usuarioId, SimuladoSessao sessao, Map<Long, String> respostas) {
        Usuario usuario = usuarios.findById(usuarioId).orElseThrow(RecursoNaoEncontradoException::new);
        Map<Long, Questao> porId = questoes.findByIdIn(sessao.ids()).stream()
                .collect(Collectors.toMap(Questao::getId, questao -> questao));
        LocalDate hoje = LocalDate.now(clock);

        List<TentativaResposta> novas = new ArrayList<>();
        Map<Long, String> escolhidas = new HashMap<>();
        int acertos = 0;
        for (Long id : sessao.ids()) {
            String resposta = respostas.get(id);
            Questao questao = porId.get(id);
            if (resposta == null || questao == null) {
                continue;
            }
            boolean correta = questao.getAlternativaCorreta().equals(resposta);
            novas.add(new TentativaResposta(usuario, questao, resposta, hoje, correta));
            escolhidas.put(id, resposta);
            if (correta) {
                acertos++;
            }
        }
        tentativas.saveAll(novas);

        int total = sessao.ids().size();
        int respondidas = novas.size();
        int aproveitamento = total == 0 ? 0 : (int) Math.round(acertos * 100.0 / total);
        long segundos = (clock.millis() - sessao.inicioMillis()) / 1000;
        List<QuestaoView> revisao = questaoService.carregar(sessao.ids(), escolhidas, true);
        return new ResultadoSimulado(total, respondidas, acertos, respondidas - acertos, total - respondidas,
                aproveitamento, Textos.duracao(segundos), revisao);
    }
}
