package com.info3c.enemPront.controller;

import com.info3c.enemPront.dto.PerfilForm;
import com.info3c.enemPront.model.Usuario;
import com.info3c.enemPront.security.ContaUsuario;
import com.info3c.enemPront.service.DesempenhoService;
import com.info3c.enemPront.service.UsuarioService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PerfilController {

    private static final LocalDate DATA_MINIMA = LocalDate.of(1900, 1, 1);

    private final UsuarioService usuarioService;
    private final DesempenhoService desempenhoService;

    public PerfilController(UsuarioService usuarioService, DesempenhoService desempenhoService) {
        this.usuarioService = usuarioService;
        this.desempenhoService = desempenhoService;
    }

    @GetMapping("/perfil")
    public String perfil(@AuthenticationPrincipal ContaUsuario conta, Model model) {
        Usuario usuario = usuarioService.buscar(conta.getId());
        PerfilForm form = new PerfilForm();
        form.setNome(usuario.getNome());
        form.setDtNascimento(usuario.getDtNascimento());
        model.addAttribute("perfil", desempenhoService.perfil(usuario));
        model.addAttribute("perfilForm", form);
        return "perfil";
    }

    @PostMapping("/perfil")
    public String salvar(@AuthenticationPrincipal ContaUsuario conta,
            @Valid @ModelAttribute("perfilForm") PerfilForm form, BindingResult result, Model model,
            RedirectAttributes redirect) {
        if (form.getDtNascimento() != null && !result.hasFieldErrors("dtNascimento")
                && form.getDtNascimento().isBefore(DATA_MINIMA)) {
            result.rejectValue("dtNascimento", "dtNascimento.invalida", "Informe uma data de nascimento válida");
        }
        if (result.hasErrors()) {
            model.addAttribute("perfil", desempenhoService.perfil(usuarioService.buscar(conta.getId())));
            return "perfil";
        }
        usuarioService.atualizarPerfil(conta.getId(), form);
        redirect.addFlashAttribute("sucesso", "Alterações salvas com sucesso");
        return "redirect:/perfil";
    }
}
