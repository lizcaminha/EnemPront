package com.info3c.enemPront.controller;

import com.info3c.enemPront.dto.FiltroQuestoes;
import com.info3c.enemPront.exception.RegraNegocioException;
import com.info3c.enemPront.security.ContaUsuario;
import com.info3c.enemPront.service.CatalogoService;
import com.info3c.enemPront.service.QuestaoService;
import com.info3c.enemPront.service.TentativaService;
import com.info3c.enemPront.web.Sessao;
import jakarta.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class QuestaoController {

    private static final Pattern RETORNO_VALIDO = Pattern.compile("/questoes(\\?[A-Za-z0-9=&%_.~+-]*)?");
    private static final String RETORNO_PADRAO = "/questoes";

    private final QuestaoService questaoService;
    private final CatalogoService catalogoService;
    private final TentativaService tentativaService;

    public QuestaoController(QuestaoService questaoService, CatalogoService catalogoService,
            TentativaService tentativaService) {
        this.questaoService = questaoService;
        this.catalogoService = catalogoService;
        this.tentativaService = tentativaService;
    }

    @GetMapping("/questoes")
    public String listar(@ModelAttribute("filtro") FiltroQuestoes filtro, BindingResult ignorado,
            @AuthenticationPrincipal ContaUsuario conta, HttpServletRequest request, Model model) {
        model.addAttribute("pagina", questaoService.buscar(filtro, Sessao.alunoId(conta)));
        model.addAttribute("areas", catalogoService.opcoesAreas());
        model.addAttribute("topicos", catalogoService.opcoesTopicos());
        model.addAttribute("anos", catalogoService.anos());
        model.addAttribute("retorno", retornoAtual(request));
        return "questoes";
    }

    @PostMapping("/questoes/{id}/responder")
    public String responder(@PathVariable("id") Long id, @RequestParam("alternativa") String alternativa,
            @RequestParam(name = "retorno", required = false) String retorno,
            @AuthenticationPrincipal ContaUsuario conta, RedirectAttributes redirect) {
        try {
            tentativaService.responder(conta.getId(), id, alternativa);
        } catch (RegraNegocioException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:" + destino(retorno) + "#q" + id;
    }

    private static String retornoAtual(HttpServletRequest request) {
        String consulta = request.getQueryString();
        return RETORNO_PADRAO + (consulta == null || consulta.isEmpty() ? "" : "?" + consulta);
    }

    private static String destino(String retorno) {
        return retorno != null && RETORNO_VALIDO.matcher(retorno).matches() ? retorno : RETORNO_PADRAO;
    }
}
