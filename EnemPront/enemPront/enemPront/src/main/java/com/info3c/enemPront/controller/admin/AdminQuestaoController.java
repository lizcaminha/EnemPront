package com.info3c.enemPront.controller.admin;

import com.info3c.enemPront.dto.FiltroQuestoes;
import com.info3c.enemPront.dto.QuestaoForm;
import com.info3c.enemPront.exception.RegraNegocioException;
import com.info3c.enemPront.service.CatalogoService;
import com.info3c.enemPront.service.QuestaoService;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/questoes")
public class AdminQuestaoController {

    private static final Set<String> NIVEIS = Set.of("A", "M", "B");
    private static final int MAXIMO_SLOTS = 10;
    private static final String LISTAGEM = "redirect:/admin/questoes";

    private final QuestaoService questaoService;
    private final CatalogoService catalogoService;

    public AdminQuestaoController(QuestaoService questaoService, CatalogoService catalogoService) {
        this.questaoService = questaoService;
        this.catalogoService = catalogoService;
    }

    @GetMapping
    public String listar(@ModelAttribute("filtro") FiltroQuestoes filtro, BindingResult ignorado, Model model) {
        model.addAttribute("pagina", questaoService.listarAdmin(filtro));
        return "admin/questoes";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        return formulario(model, new QuestaoForm(), "/admin/questoes", "Nova questão");
    }

    @PostMapping
    public String criar(@Valid @ModelAttribute("form") QuestaoForm form, BindingResult result, Model model,
            RedirectAttributes redirect) {
        return gravar(form, result, model, redirect, null, "/admin/questoes", "Nova questão");
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable("id") Long id, Model model) {
        return formulario(model, questaoService.formularioDe(id), "/admin/questoes/" + id, "Editar questão #" + id);
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable("id") Long id, @Valid @ModelAttribute("form") QuestaoForm form,
            BindingResult result, Model model, RedirectAttributes redirect) {
        return gravar(form, result, model, redirect, id, "/admin/questoes/" + id, "Editar questão #" + id);
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes redirect) {
        try {
            questaoService.excluir(id);
            redirect.addFlashAttribute("sucesso", "Questão excluída");
        } catch (RegraNegocioException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return LISTAGEM;
    }

    private String gravar(QuestaoForm form, BindingResult result, Model model, RedirectAttributes redirect,
            Long id, String acao, String titulo) {
        validarTopicos(form, result);
        if (!result.hasErrors()) {
            try {
                questaoService.salvar(form, id);
                redirect.addFlashAttribute("sucesso", "Questão salva com sucesso");
                return LISTAGEM;
            } catch (RegraNegocioException e) {
                result.reject("questao.invalida", e.getMessage());
            }
        }
        return formulario(model, form, acao, titulo);
    }

    private String formulario(Model model, QuestaoForm form, String acao, String titulo) {
        model.addAttribute("form", form);
        model.addAttribute("acao", acao);
        model.addAttribute("titulo", titulo);
        model.addAttribute("edicoes", catalogoService.opcoesEdicoes());
        model.addAttribute("topicosDisponiveis", catalogoService.opcoesTopicos());
        return "admin/questao-form";
    }

    private void validarTopicos(QuestaoForm form, BindingResult result) {
        if (form.getTopicos() == null || form.getTopicos().size() > MAXIMO_SLOTS) {
            result.reject("topicos.invalidos", "Seleção de tópicos inválida");
            return;
        }
        Set<Long> vistos = new HashSet<>();
        boolean algum = false;
        for (QuestaoForm.TopicoSlot slot : form.getTopicos()) {
            if (slot.getTopicoId() == null) {
                continue;
            }
            algum = true;
            if (!vistos.add(slot.getTopicoId())) {
                result.reject("topicos.duplicado", "O mesmo tópico foi selecionado mais de uma vez");
            }
            if (slot.getDificuldade() == null || !NIVEIS.contains(slot.getDificuldade())) {
                result.reject("topicos.dificuldade", "Selecione a dificuldade de cada tópico escolhido");
            }
        }
        if (!algum) {
            result.reject("topicos.obrigatorio", "Associe ao menos um tópico à questão");
        }
    }
}
