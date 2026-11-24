package com.info3c.enemPront.web;

import com.info3c.enemPront.dto.UsuarioLogado;
import com.info3c.enemPront.model.Usuario;
import com.info3c.enemPront.security.ContaUsuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAdvice {

    @ModelAttribute("usuarioLogado")
    public UsuarioLogado usuarioLogado(HttpServletRequest request) {
        Authentication autenticacao = SecurityContextHolder.getContext().getAuthentication();
        Object principal = autenticacao == null ? null : autenticacao.getPrincipal();
        if (!(principal instanceof ContaUsuario conta)) {
            return null;
        }
        if (conta.isAdmin()) {
            return new UsuarioLogado(null, conta.getNome(), true);
        }
        Object atual = request.getAttribute(ContaAtivaInterceptor.ATRIBUTO_USUARIO);
        if (atual instanceof Usuario usuario) {
            return new UsuarioLogado(usuario.getId(), usuario.getNome(), false);
        }
        return new UsuarioLogado(conta.getId(), conta.getNome(), false);
    }
}
