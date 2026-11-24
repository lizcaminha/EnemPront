package com.info3c.enemPront.controller;

import com.info3c.enemPront.dto.CadastroForm;
import com.info3c.enemPront.exception.EmailJaCadastradoException;
import com.info3c.enemPront.service.UsuarioService;
import com.info3c.enemPront.util.SenhaPolicy;
import jakarta.validation.Valid;
import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CadastroController {

    private static final LocalDate DATA_MINIMA = LocalDate.of(1900, 1, 1);

    private final UsuarioService usuarioService;

    public CadastroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/cadastro")
    public String formulario(Model model) {
        model.addAttribute("cadastroForm", new CadastroForm());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(@Valid @ModelAttribute("cadastroForm") CadastroForm form, BindingResult result,
            RedirectAttributes redirect) {
        validarDataDeNascimento(form, result);
        validarSenha(form, result);
        if (!result.hasErrors()) {
            try {
                usuarioService.cadastrar(form);
            } catch (EmailJaCadastradoException e) {
                result.rejectValue("email", "email.duplicado", e.getMessage());
            }
        }
        if (result.hasErrors()) {
            form.setSenha(null);
            form.setConfirmarSenha(null);
            return "cadastro";
        }
        redirect.addFlashAttribute("sucesso", "Conta criada! Entre para começar a estudar.");
        return "redirect:/login";
    }

    private void validarDataDeNascimento(CadastroForm form, BindingResult result) {
        if (form.getDtNascimento() != null && !result.hasFieldErrors("dtNascimento")
                && form.getDtNascimento().isBefore(DATA_MINIMA)) {
            result.rejectValue("dtNascimento", "dtNascimento.invalida", "Informe uma data de nascimento válida");
        }
    }

    private void validarSenha(CadastroForm form, BindingResult result) {
        if (result.hasFieldErrors("senha")) {
            return;
        }
        var violacoes = SenhaPolicy.violacoes(form.getSenha());
        if (!violacoes.isEmpty()) {
            result.rejectValue("senha", "senha.fraca", violacoes.get(0));
            return;
        }
        if (!result.hasFieldErrors("confirmarSenha") && !form.getSenha().equals(form.getConfirmarSenha())) {
            result.rejectValue("confirmarSenha", "senha.diferente", "As senhas não coincidem");
        }
    }
}
