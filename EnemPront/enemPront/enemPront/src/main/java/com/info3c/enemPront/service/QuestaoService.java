package com.info3c.enemPront.service;

import com.info3c.enemPront.dto.AlternativaView;
import com.info3c.enemPront.dto.FiltroQuestoes;
import com.info3c.enemPront.dto.Pagina;
import com.info3c.enemPront.dto.QuestaoForm;
import com.info3c.enemPront.dto.QuestaoResumo;
import com.info3c.enemPront.dto.QuestaoView;
import com.info3c.enemPront.exception.RecursoNaoEncontradoException;
import com.info3c.enemPront.exception.RegraNegocioException;
import com.info3c.enemPront.model.EdicaoEnem;
import com.info3c.enemPront.model.Questao;
import com.info3c.enemPront.model.QuestaoTopico;
import com.info3c.enemPront.model.Topico;
import com.info3c.enemPront.repository.EdicaoEnemRepository;
import com.info3c.enemPront.repository.QuestaoRepository;
import com.info3c.enemPront.repository.QuestaoTopicoRepository;
import com.info3c.enemPront.repository.TentativaRespostaRepository;
import com.info3c.enemPront.repository.TopicoRepository;
import com.info3c.enemPront.util.Textos;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestaoService {

    public static final int TAMANHO_PAGINA = 10;
    public static final int TAMANHO_PAGINA_ADMIN = 15;

    private static final List<String> LETRAS = List.of("A", "B", "C", "D", "E");
    private static final Map<String, String> ROTULO_DIFICULDADE = Map.of("B", "FÁCIL", "M", "MÉDIO", "A", "DIFÍCIL");
    private static final Set<String> NIVEIS = Set.of("A", "M", "B");
    private static final int RESUMO_ENUNCIADO = 140;

    private final EntityManager em;
    private final QuestaoRepository questoes;
    private final QuestaoTopicoRepository questaoTopicos;
    private final TentativaRespostaRepository tentativas;
    private final EdicaoEnemRepository edicoes;
    private final TopicoRepository topicos;

    public QuestaoService(EntityManager em, QuestaoRepository questoes, QuestaoTopicoRepository questaoTopicos,
            TentativaRespostaRepository tentativas, EdicaoEnemRepository edicoes, TopicoRepository topicos) {
        this.em = em;
        this.questoes = questoes;
        this.questaoTopicos = questaoTopicos;
        this.tentativas = tentativas;
        this.edicoes = edicoes;
        this.topicos = topicos;
    }

    @Transactional(readOnly = true)
    public Pagina<QuestaoView> buscar(FiltroQuestoes filtro, Long usuarioId) {
        Pagina<Questao> pagina = consultar(filtro, TAMANHO_PAGINA);
        List<Long> ids = pagina.itens().stream().map(Questao::getId).toList();
        Map<Long, String> escolhidas = usuarioId == null ? Map.of() : ultimasEscolhas(usuarioId, ids);
        List<QuestaoView> views = montar(pagina.itens(), escolhidas, false);
        return new Pagina<>(views, pagina.numero(), pagina.totalPaginas(), pagina.total());
    }

    @Transactional(readOnly = true)
    public List<QuestaoView> carregar(List<Long> ids, Map<Long, String> escolhidas, boolean revelarGabarito) {
        if (ids.isEmpty()) {
            return List.of();
        }
        Map<Long, Questao> porId = questoes.findByIdIn(ids).stream()
                .collect(Collectors.toMap(Questao::getId, questao -> questao));
        List<Questao> ordenadas = ids.stream().map(porId::get).filter(Objects::nonNull).toList();
        return montar(ordenadas, escolhidas, revelarGabarito);
    }

    @Transactional(readOnly = true)
    public Pagina<QuestaoResumo> listarAdmin(FiltroQuestoes filtro) {
        Pagina<Questao> pagina = consultar(filtro, TAMANHO_PAGINA_ADMIN);
        List<Long> ids = pagina.itens().stream().map(Questao::getId).toList();
        Map<Long, List<String>> nomesDeTopicos = new HashMap<>();
        if (!ids.isEmpty()) {
            for (QuestaoTopico qt : questaoTopicos.buscarComTopico(ids)) {
                nomesDeTopicos.computeIfAbsent(qt.getId().getQuestaoId(), chave -> new ArrayList<>())
                        .add(qt.getTopico().getNome());
            }
        }
        List<QuestaoResumo> resumos = new ArrayList<>();
        for (Questao q : pagina.itens()) {
            EdicaoEnem edicao = q.getEdicaoEnem();
            resumos.add(new QuestaoResumo(
                    q.getId(),
                    "ENEM " + edicao.getAnoAplicacao() + " (" + edicao.getTipoAplicacao() + ")",
                    Textos.resumir(q.getEnunciado(), RESUMO_ENUNCIADO),
                    q.getAlternativaCorreta(),
                    String.join(", ", nomesDeTopicos.getOrDefault(q.getId(), List.of()))));
        }
        return new Pagina<>(resumos, pagina.numero(), pagina.totalPaginas(), pagina.total());
    }

    @Transactional(readOnly = true)
    public QuestaoForm formularioDe(Long id) {
        Questao q = questoes.findById(id).orElseThrow(RecursoNaoEncontradoException::new);
        QuestaoForm form = new QuestaoForm();
        form.setEdicaoId(q.getEdicaoEnem().getId());
        form.setEnunciado(q.getEnunciado());
        form.setAlternativaA(q.getAlternativaA());
        form.setAlternativaB(q.getAlternativaB());
        form.setAlternativaC(q.getAlternativaC());
        form.setAlternativaD(q.getAlternativaD());
        form.setAlternativaE(q.getAlternativaE());
        form.setAlternativaCorreta(q.getAlternativaCorreta());
        List<QuestaoTopico> existentes = new ArrayList<>(questaoTopicos.porQuestao(id));
        existentes.sort(Comparator.comparing((QuestaoTopico qt) -> qt.getId().getTopicoId()));
        for (int i = 0; i < existentes.size() && i < form.getTopicos().size(); i++) {
            form.getTopicos().get(i).setTopicoId(existentes.get(i).getId().getTopicoId());
            form.getTopicos().get(i).setDificuldade(existentes.get(i).getDificuldade());
        }
        return form;
    }

    @Transactional
    public Questao salvar(QuestaoForm form, Long id) {
        EdicaoEnem edicao = edicoes.findById(form.getEdicaoId())
                .orElseThrow(() -> new RegraNegocioException("Edição inválida"));
        Questao questao = id == null
                ? new Questao()
                : questoes.findById(id).orElseThrow(RecursoNaoEncontradoException::new);
        questao.setEdicaoEnem(edicao);
        questao.setEnunciado(form.getEnunciado().trim());
        questao.setAlternativaA(form.getAlternativaA().trim());
        questao.setAlternativaB(form.getAlternativaB().trim());
        questao.setAlternativaC(form.getAlternativaC().trim());
        questao.setAlternativaD(form.getAlternativaD().trim());
        questao.setAlternativaE(form.getAlternativaE().trim());
        questao.setAlternativaCorreta(Textos.alternativa(form.getAlternativaCorreta()));
        Questao salva = questoes.save(questao);
        sincronizarTopicos(salva, form.getTopicos());
        return salva;
    }

    @Transactional
    public void excluir(Long id) {
        if (tentativas.contarPorQuestao(id) > 0) {
            throw new RegraNegocioException("Esta questão já foi respondida por alunos e não pode ser excluída");
        }
        questaoTopicos.excluirPorQuestao(id);
        questoes.deleteById(id);
    }

    private void sincronizarTopicos(Questao questao, List<QuestaoForm.TopicoSlot> slots) {
        Map<Long, String> desejados = new LinkedHashMap<>();
        for (QuestaoForm.TopicoSlot slot : slots) {
            if (slot.getTopicoId() == null) {
                continue;
            }
            String dificuldade = slot.getDificuldade() == null ? "" : slot.getDificuldade().trim().toUpperCase(Locale.ROOT);
            if (!NIVEIS.contains(dificuldade)) {
                throw new RegraNegocioException("Selecione a dificuldade de cada tópico");
            }
            desejados.put(slot.getTopicoId(), dificuldade);
        }
        if (desejados.isEmpty()) {
            throw new RegraNegocioException("Associe ao menos um tópico à questão");
        }
        for (QuestaoTopico existente : questaoTopicos.porQuestao(questao.getId())) {
            String dificuldade = desejados.remove(existente.getId().getTopicoId());
            if (dificuldade == null) {
                em.remove(existente);
            } else {
                existente.setDificuldade(dificuldade);
            }
        }
        for (Map.Entry<Long, String> novo : desejados.entrySet()) {
            Topico topico = topicos.findById(novo.getKey())
                    .orElseThrow(() -> new RegraNegocioException("Tópico inválido"));
            em.persist(new QuestaoTopico(questao, topico, novo.getValue()));
        }
    }

    private Pagina<Questao> consultar(FiltroQuestoes filtro, int tamanho) {
        StringBuilder where = new StringBuilder(" where 1 = 1");
        Map<String, Object> parametros = new HashMap<>();
        if (filtro.getAno() != null) {
            where.append(" and e.anoAplicacao = :ano");
            parametros.put("ano", filtro.getAno());
        }
        if (filtro.getQ() != null) {
            where.append(" and lower(q.enunciado) like :texto escape '!'");
            parametros.put("texto", "%" + Textos.escaparLike(filtro.getQ().toLowerCase(Locale.ROOT)) + "%");
        }
        List<String> condicoes = new ArrayList<>();
        if (filtro.getArea() != null) {
            condicoes.add("tp.area.id = :area");
            parametros.put("area", filtro.getArea());
        }
        if (filtro.getTopico() != null) {
            condicoes.add("tp.id = :topico");
            parametros.put("topico", filtro.getTopico());
        }
        if (filtro.getDificuldade() != null) {
            condicoes.add("qt.dificuldade = :dificuldade");
            parametros.put("dificuldade", filtro.getDificuldade());
        }
        if (filtro.getIncidencia() != null) {
            condicoes.add("tp.incidenciaEnem = :incidencia");
            parametros.put("incidencia", filtro.getIncidencia());
        }
        if (!condicoes.isEmpty()) {
            where.append(" and exists (select 1 from QuestaoTopico qt join qt.topico tp "
                    + "where qt.id.questaoId = q.id and ")
                    .append(String.join(" and ", condicoes))
                    .append(")");
        }

        String origem = " from Questao q join q.edicaoEnem e" + where;
        TypedQuery<Long> contagem = em.createQuery("select count(q)" + origem, Long.class);
        aplicar(contagem, parametros);
        long total = contagem.getSingleResult();

        int totalPaginas = (int) Math.max(1, (total + tamanho - 1) / tamanho);
        int numero = Math.min(filtro.getPagina(), totalPaginas - 1);

        TypedQuery<Questao> consulta = em.createQuery(
                "select q" + origem + " order by e.anoAplicacao desc, q.id asc", Questao.class);
        aplicar(consulta, parametros);
        consulta.setFirstResult(numero * tamanho);
        consulta.setMaxResults(tamanho);
        return new Pagina<>(consulta.getResultList(), numero, totalPaginas, total);
    }

    private static void aplicar(TypedQuery<?> consulta, Map<String, Object> parametros) {
        for (Map.Entry<String, Object> parametro : parametros.entrySet()) {
            consulta.setParameter(parametro.getKey(), parametro.getValue());
        }
    }

    private Map<Long, String> ultimasEscolhas(Long usuarioId, Collection<Long> ids) {
        Map<Long, String> escolhas = new HashMap<>();
        if (ids.isEmpty()) {
            return escolhas;
        }
        for (Object[] linha : tentativas.ultimasRespostas(usuarioId, ids)) {
            escolhas.putIfAbsent((Long) linha[0], (String) linha[1]);
        }
        return escolhas;
    }

    private List<QuestaoView> montar(List<Questao> lista, Map<Long, String> escolhidas, boolean revelarGabarito) {
        if (lista.isEmpty()) {
            return List.of();
        }
        List<Long> ids = lista.stream().map(Questao::getId).toList();
        Map<Long, List<QuestaoTopico>> porQuestao = new HashMap<>();
        for (QuestaoTopico qt : questaoTopicos.buscarComTopico(ids)) {
            porQuestao.computeIfAbsent(qt.getId().getQuestaoId(), chave -> new ArrayList<>()).add(qt);
        }
        List<QuestaoView> views = new ArrayList<>();
        for (Questao q : lista) {
            views.add(montarView(q, porQuestao.getOrDefault(q.getId(), List.of()),
                    escolhidas.get(q.getId()), revelarGabarito));
        }
        return views;
    }

    private QuestaoView montarView(Questao q, List<QuestaoTopico> vinculos, String escolhida, boolean revelarGabarito) {
        List<QuestaoTopico> ordenados = new ArrayList<>(vinculos);
        ordenados.sort(Comparator.comparing((QuestaoTopico qt) -> qt.getTopico().getId()));

        List<String> tags = new ArrayList<>();
        tags.add("ENEM " + q.getEdicaoEnem().getAnoAplicacao());
        if (!ordenados.isEmpty()) {
            String dificuldade = ordenados.get(0).getDificuldade();
            tags.add(ROTULO_DIFICULDADE.getOrDefault(dificuldade, dificuldade));
        }
        Set<String> materias = new LinkedHashSet<>();
        for (QuestaoTopico qt : ordenados) {
            materias.add(qt.getTopico().getMateria().toUpperCase(Locale.ROOT));
        }
        tags.addAll(materias);
        if (ordenados.stream().anyMatch(qt -> "A".equals(qt.getTopico().getIncidenciaEnem()))) {
            tags.add("⚡ ALTA INCIDÊNCIA");
        }

        String correta = q.getAlternativaCorreta();
        boolean respondida = escolhida != null;
        boolean revelar = respondida || revelarGabarito;
        List<AlternativaView> alternativas = new ArrayList<>();
        for (String letra : LETRAS) {
            String estado = "";
            if (revelar) {
                if (letra.equals(correta)) {
                    estado = "ok";
                } else if (letra.equals(escolhida)) {
                    estado = "bad";
                }
            }
            alternativas.add(new AlternativaView(letra, letra + ") " + q.textoDaAlternativa(letra), estado));
        }

        boolean acertou = respondida && correta.equals(escolhida);
        String mensagem = null;
        if (revelar) {
            if (!respondida) {
                mensagem = "Sem resposta. Gabarito: " + correta;
            } else if (acertou) {
                mensagem = "✅ Resposta correta!";
            } else {
                mensagem = "❌ Resposta incorreta. Gabarito: " + correta;
            }
        }
        return new QuestaoView(q.getId(), tags, q.getEnunciado(), alternativas, respondida, acertou, mensagem);
    }
}
