package com.info3c.enemPront.security;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class LoginAttemptService {

    static final int MAXIMO_FALHAS = 5;
    static final Duration JANELA = Duration.ofMinutes(15);
    private static final int LIMITE_PARA_LIMPEZA = 5000;

    private record Registro(int falhas, Instant ultima) {
    }

    private final Clock clock;
    private final ConcurrentHashMap<String, Registro> registros = new ConcurrentHashMap<>();

    public LoginAttemptService(Clock clock) {
        this.clock = clock;
    }

    public void registrarFalha(String chave) {
        if (chave == null || chave.isBlank()) {
            return;
        }
        Instant agora = clock.instant();
        limparExpirados(agora);
        registros.merge(chave, new Registro(1, agora),
                (atual, novo) -> expirado(atual, agora) ? novo : new Registro(atual.falhas() + 1, agora));
    }

    public boolean estaBloqueado(String chave) {
        if (chave == null) {
            return false;
        }
        Registro registro = registros.get(chave);
        return registro != null && !expirado(registro, clock.instant()) && registro.falhas() >= MAXIMO_FALHAS;
    }

    public void limpar(String chave) {
        if (chave != null) {
            registros.remove(chave);
        }
    }

    private boolean expirado(Registro registro, Instant agora) {
        return registro.ultima().plus(JANELA).isBefore(agora);
    }

    private void limparExpirados(Instant agora) {
        if (registros.size() > LIMITE_PARA_LIMPEZA) {
            registros.values().removeIf(registro -> expirado(registro, agora));
        }
    }
}
