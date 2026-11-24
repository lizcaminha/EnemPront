package com.info3c.enemPront.service;

import com.info3c.enemPront.dto.Opcao;
import com.info3c.enemPront.exception.RegraNegocioException;
import com.info3c.enemPront.model.AreaConhecimento;
import com.info3c.enemPront.model.EdicaoEnem;
import com.info3c.enemPront.model.Topico;
import com.info3c.enemPront.repository.AreaConhecimentoRepository;
import com.info3c.enemPront.repository.EdicaoEnemRepository;
import com.info3c.enemPront.repository.QuestaoRepository;
import com.info3c.enemPront.repository.QuestaoTopicoRepository;
import com.info3c.enemPront.repository.TopicoRepository;
import java.time.Year;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CatalogoService {

    private static final int PRIMEIRO_ANO_ENEM = 1998;
    private static final Set<String> NIVEIS = Set.of("A", "M", "B");
    private static final Set<String> TIPOS_APLICACAO = Set.of("R", "EXT", "PPL");

    private final AreaConhecimentoRepository areas;
    private final TopicoRepository topicos;
    private final EdicaoEnemRepository edicoes;
    private final QuestaoRepository questoes;
    private final QuestaoTopicoRepository questaoTopicos;

    public CatalogoService(AreaConhecimentoRepository areas, TopicoRepository topicos, EdicaoEnemRepository edicoes,
            QuestaoRepository questoes, QuestaoTopicoRepository questaoTopicos) {
        this.areas = areas;
        this.topicos = topicos;
        this.edicoes = edicoes;
        this.questoes = questoes;
        this.questaoTopicos = questaoTopicos;
    }

    @Transactional(readOnly = true)
    public List<AreaConhecimento> areas() {
        return areas.findAllByOrderByNomeAsc();
    }

    @Transactional(readOnly = true)
    public List<Topico> topicos() {
        return topicos.findAllByOrderByNomeAsc();
    }

    @Transactional(readOnly = true)
    public List<EdicaoEnem> edicoes() {
        return edicoes.findAllByOrderByAnoAplicacaoDescTipoAplicacaoAsc();
    }

    @Transactional(readOnly = true)
    public List<Opcao> opcoesAreas() {
        return areas().stream().map(a -> new Opcao(a.getId(), a.getNome())).toList();
    }

    @Transactional(readOnly = true)
    public List<Opcao> opcoesTopicos() {
        return topicos().stream()
                .map(t -> new Opcao(t.getId(), t.getNome() + " (" + t.getMateria() + ")"))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Opcao> opcoesEdicoes() {
        return edicoes().stream()
                .map(e -> new Opcao(e.getId(), "ENEM " + e.getAnoAplicacao() + " (" + e.getTipoAplicacao() + ")"))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Integer> anos() {
        return edicoes().stream().map(EdicaoEnem::getAnoAplicacao).distinct().toList();
    }

    @Transactional
    public AreaConhecimento criarArea(String nome) {
        AreaConhecimento area = new AreaConhecimento();
        area.setNome(textoObrigatorio(nome, 50, "Nome da área"));
        return areas.save(area);
    }

    @Transactional
    public void excluirArea(Long id) {
        if (topicos.contarPorArea(id) > 0) {
            throw new RegraNegocioException("A área possui tópicos cadastrados e não pode ser excluída");
        }
        areas.deleteById(id);
    }

    @Transactional
    public Topico criarTopico(Long areaId, String materia, String nome, String incidencia) {
        AreaConhecimento area = areas.findById(areaId == null ? -1L : areaId)
                .orElseThrow(() -> new RegraNegocioException("Selecione a área de conhecimento"));
        String nivel = incidencia == null ? "" : incidencia.trim().toUpperCase(Locale.ROOT);
        if (!NIVEIS.contains(nivel)) {
            throw new RegraNegocioException("Selecione a incidência no ENEM");
        }
        Topico topico = new Topico();
        topico.setArea(area);
        topico.setMateria(textoObrigatorio(materia, 25, "Matéria"));
        topico.setNome(textoObrigatorio(nome, 30, "Nome do tópico"));
        topico.setIncidenciaEnem(nivel);
        return topicos.save(topico);
    }

    @Transactional
    public void excluirTopico(Long id) {
        if (questaoTopicos.contarPorTopico(id) > 0) {
            throw new RegraNegocioException("O tópico está associado a questões e não pode ser excluído");
        }
        topicos.deleteById(id);
    }

    @Transactional
    public EdicaoEnem criarEdicao(Integer ano, String tipo) {
        int limite = Year.now().getValue() + 1;
        if (ano == null || ano < PRIMEIRO_ANO_ENEM || ano > limite) {
            throw new RegraNegocioException("Informe um ano entre " + PRIMEIRO_ANO_ENEM + " e " + limite);
        }
        String tipoNormalizado = tipo == null ? "" : tipo.trim().toUpperCase(Locale.ROOT);
        if (!TIPOS_APLICACAO.contains(tipoNormalizado)) {
            throw new RegraNegocioException("Selecione o tipo de aplicação");
        }
        if (edicoes.existsByAnoAplicacaoAndTipoAplicacao(ano, tipoNormalizado)) {
            throw new RegraNegocioException("Essa edição já está cadastrada");
        }
        EdicaoEnem edicao = new EdicaoEnem();
        edicao.setAnoAplicacao(ano);
        edicao.setTipoAplicacao(tipoNormalizado);
        return edicoes.save(edicao);
    }

    @Transactional
    public void excluirEdicao(Long id) {
        if (questoes.contarPorEdicao(id) > 0) {
            throw new RegraNegocioException("A edição possui questões cadastradas e não pode ser excluída");
        }
        edicoes.deleteById(id);
    }

    private static String textoObrigatorio(String valor, int maximo, String campo) {
        String texto = valor == null ? "" : valor.trim();
        if (texto.isEmpty()) {
            throw new RegraNegocioException(campo + " é obrigatório");
        }
        if (texto.length() > maximo) {
            throw new RegraNegocioException(campo + " deve ter no máximo " + maximo + " caracteres");
        }
        return texto;
    }
}
