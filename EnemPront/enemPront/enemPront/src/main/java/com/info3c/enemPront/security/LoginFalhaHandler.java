package com.info3c.enemPront.security;

import com.info3c.enemPront.util.Textos;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginFalhaHandler implements AuthenticationFailureHandler {

    private static final String PARAMETRO_EMAIL = "email";
    private static final String PARAMETRO_ORIGEM = "origem";
    private static final String ORIGEM_ADMIN = "admin";

    private final LoginAttemptService tentativas;

    public LoginFalhaHandler(LoginAttemptService tentativas) {
        this.tentativas = tentativas;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException {
        String email = Textos.normalizarEmail(request.getParameter(PARAMETRO_EMAIL));
        if (!(exception instanceof LockedException)) {
            tentativas.registrarFalha(email);
        }
        String pagina = ORIGEM_ADMIN.equals(request.getParameter(PARAMETRO_ORIGEM)) ? "/admin-login" : "/login";
        String motivo = tentativas.estaBloqueado(email) ? "bloqueado" : "error";
        response.sendRedirect(request.getContextPath() + pagina + "?" + motivo);
    }
}
