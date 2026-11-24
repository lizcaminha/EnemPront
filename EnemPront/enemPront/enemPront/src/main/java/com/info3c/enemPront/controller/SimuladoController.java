package com.info3c.enemPront.controller;

import com.info3c.enemPront.dto.ResultadoSimulado;
import com.info3c.enemPront.dto.SimuladoSessao;
import com.info3c.enemPront.exception.RegraNegocioException;
import com.info3c.enemPront.security.ContaUsuario;
import com.info3c.enemPront.service.CatalogoService;
import com.info3c.enemPront.service.SimuladoService;
import com.info3c.enemPront.web.Sessao;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SimuladoController {

    private static final String ATRIBUTO_SIMULADO = "simulado";
    private static final Pattern ALTERNATIVA = Pattern.compile("[A-E]");

    private final SimuladoService simuladoService;
    private final CatalogoService catalogoService;

    public SimuladoController(SimuladoService simuladoService, CatalogoService catalogoService) {
        this.simuladoService = simuladoService;
        this.catalogoService = catalogoService;
    }

    @GetMapping("/simulado")
    public String configurar(@AuthenticationPrincipal ContaUsuario conta, Model model) {
        boolean aluno = Sessao.alunoId(conta) != null;
        model.addAttribute("aluno", aluno);
        if (aluno) {
            model.addAttribute("areas", catalogoService.opcoesAreas());
            model.addAttribute("minutosPorQuestao", SimuladoService.MINUTOS_POR_QUESTAO);
        }
        return "simulado";
    }

    @PostMapping("/simulado/iniciar")
    public String iniciar(@RequestParam(name = "quantidade", required = false) Integer quantidade,
            @RequestParam(name = "area", required = false) Long area, HttpSession sessao,
            RedirectAttributes redirect) {
        try {
            sessao.setAttribute(ATRIBUTO_SIMULADO, simuladoService.iniciar(quantidade, area));
        } catch (RegraNegocioException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
            return "redirect:/simulado";
        }
        return "redirect:/simulado/prova";
    }

    @GetMapping("/simulado/prova")
    public String prova(HttpSession sessao, Model model) {
        SimuladoSessao simulado = (SimuladoSessao) sessao.getAttribute(ATRIBUTO_SIMULADO);
        if (simulado == null) {
            return "redirect:/simulado";
        }
        model.addAttribute("prova", simuladoService.prova(simulado));
        return "simulado-prova";
    }

    @PostMapping("/simulado/finalizar")
    public String finalizar(@RequestParam Map<String, String> parametros,
            @AuthenticationPrincipal ContaUsuario conta, HttpSession sessao, RedirectAttributes redirect) {
        SimuladoSessao simulado = (SimuladoSessao) sessao.getAttribute(ATRIBUTO_SIMULADO);
        if (simulado == null) {
            return "redirect:/simulado";
        }
        Map<Long, String> respostas = new HashMap<>();
        for (Long id : simulado.ids()) {
            String valor = parametros.get("r_" + id);
            if (valor != null && ALTERNATIVA.matcher(valor).matches()) {
                respostas.put(id, valor);
            }
        }
        ResultadoSimulado resultado = simuladoService.finalizar(conta.getId(), simulado, respostas);
        sessao.removeAttribute(ATRIBUTO_SIMULADO);
        redirect.addFlashAttribute("resultado", resultado);
        return "redirect:/simulado/resultado";
    }

    @GetMapping("/simulado/resultado")
    public String resultado(Model model) {
        if (!model.containsAttribute("resultado")) {
            return "redirect:/simulado";
        }
        return "simulado-resultado";
    }
}
