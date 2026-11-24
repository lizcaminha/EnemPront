package com.info3c.enemPront.web;

import com.info3c.enemPront.model.Usuario;
import com.info3c.enemPront.repository.UsuarioRepository;
import com.info3c.enemPront.security.ContaUsuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ContaAtivaInterceptor implements HandlerInterceptor {

    public static final String ATRIBUTO_USUARIO = "usuarioAtual";

    private final UsuarioRepository usuarios;

    public ContaAtivaInterceptor(UsuarioRepository usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        Authentication autenticacao = SecurityContextHolder.getContext().getAuthentication();
        Object principal = autenticacao == null ? null : autenticacao.getPrincipal();
        if (!(principal instanceof ContaUsuario conta) || conta.isAdmin()) {
            return true;
        }
        Optional<Usuario> usuario = usuarios.findById(conta.getId()).filter(Usuario::isAtivo);
        if (usuario.isEmpty()) {
            HttpSession sessao = request.getSession(false);
            if (sessao != null) {
                sessao.invalidate();
            }
            SecurityContextHolder.clearContext();
            response.sendRedirect(request.getContextPath() + "/login?error");
            return false;
        }
        request.setAttribute(ATRIBUTO_USUARIO, usuario.get());
        return true;
    }
}
