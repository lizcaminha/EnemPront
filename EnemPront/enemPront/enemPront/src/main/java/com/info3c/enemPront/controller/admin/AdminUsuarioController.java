package com.info3c.enemPront.controller.admin;

import com.info3c.enemPront.service.RelatorioService;
import com.info3c.enemPront.service.UsuarioService;
import java.nio.charset.StandardCharsets;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/usuarios")
public class AdminUsuarioController {

    private final RelatorioService relatorioService;
    private final UsuarioService usuarioService;

    public AdminUsuarioController(RelatorioService relatorioService, UsuarioService usuarioService) {
        this.relatorioService = relatorioService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", relatorioService.usuarios());
        return "admin/usuarios";
    }

    @PostMapping("/{id}/status")
    public String alterarStatus(@PathVariable("id") Long id, @RequestParam("ativo") boolean ativo,
            RedirectAttributes redirect) {
        usuarioService.alterarStatus(id, ativo);
        redirect.addFlashAttribute("sucesso", ativo ? "Conta ativada" : "Conta inativada");
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/relatorio.csv")
    public ResponseEntity<byte[]> exportar() {
        byte[] corpo = relatorioService.csv(relatorioService.usuarios()).getBytes(StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"usuarios-enempront.csv\"")
                .contentType(new MediaType("text", "csv", StandardCharsets.UTF_8))
                .body(corpo);
    }
}
