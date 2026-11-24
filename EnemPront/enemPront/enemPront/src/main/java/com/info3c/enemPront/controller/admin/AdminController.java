package com.info3c.enemPront.controller.admin;

import com.info3c.enemPront.service.PainelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final PainelService painelService;

    public AdminController(PainelService painelService) {
        this.painelService = painelService;
    }

    @GetMapping("/admin")
    public String painel(Model model) {
        model.addAttribute("indicadores", painelService.totais());
        return "admin/painel";
    }
}
