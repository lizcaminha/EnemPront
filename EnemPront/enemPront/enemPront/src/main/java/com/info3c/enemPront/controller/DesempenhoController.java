package com.info3c.enemPront.controller;

import com.info3c.enemPront.security.ContaUsuario;
import com.info3c.enemPront.service.DesempenhoService;
import com.info3c.enemPront.web.Sessao;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DesempenhoController {

    private final DesempenhoService desempenhoService;

    public DesempenhoController(DesempenhoService desempenhoService) {
        this.desempenhoService = desempenhoService;
    }

    @GetMapping("/desempenho")
    public String desempenho(@AuthenticationPrincipal ContaUsuario conta, Model model) {
        Long alunoId = Sessao.alunoId(conta);
        model.addAttribute("aluno", alunoId != null);
        if (alunoId != null) {
            model.addAttribute("d", desempenhoService.calcular(alunoId));
        }
        return "desempenho";
    }
}
