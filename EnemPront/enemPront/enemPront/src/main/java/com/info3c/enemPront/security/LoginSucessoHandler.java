package com.info3c.enemPront.security;

import com.info3c.enemPront.util.Textos;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginSucessoHandler implements AuthenticationSuccessHandler {

    private final LoginAttemptService tentativas;

    public LoginSucessoHandler(LoginAttemptService tentativas) {
        this.tentativas = tentativas;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        tentativas.limpar(Textos.normalizarEmail(authentication.getName()));
        boolean admin = authentication.getPrincipal() instanceof ContaUsuario conta && conta.isAdmin();
        response.sendRedirect(request.getContextPath() + (admin ? "/admin" : "/questoes"));
    }
}
