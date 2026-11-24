package com.info3c.enemPront.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;

class LoginAttemptServiceTest {

    private static final class RelogioMutavel extends Clock {

        private Instant agora = Instant.parse("2026-11-15T12:00:00Z");

        void avancar(Duration duracao) {
            agora = agora.plus(duracao);
        }

        @Override
        public ZoneId getZone() {
            return ZoneId.of("UTC");
        }

        @Override
        public Clock withZone(ZoneId zone) {
            return this;
        }

        @Override
        public Instant instant() {
            return agora;
        }
    }

    @Test
    void bloqueiaAposCincoFalhas() {
        LoginAttemptService servico = new LoginAttemptService(new RelogioMutavel());
        for (int i = 0; i < LoginAttemptService.MAXIMO_FALHAS - 1; i++) {
            servico.registrarFalha("aluno@exemplo.com");
        }
        assertFalse(servico.estaBloqueado("aluno@exemplo.com"));
        servico.registrarFalha("aluno@exemplo.com");
        assertTrue(servico.estaBloqueado("aluno@exemplo.com"));
        assertFalse(servico.estaBloqueado("outro@exemplo.com"));
    }

    @Test
    void desbloqueiaAposAJanela() {
        RelogioMutavel relogio = new RelogioMutavel();
        LoginAttemptService servico = new LoginAttemptService(relogio);
        for (int i = 0; i < LoginAttemptService.MAXIMO_FALHAS; i++) {
            servico.registrarFalha("aluno@exemplo.com");
        }
        assertTrue(servico.estaBloqueado("aluno@exemplo.com"));
        relogio.avancar(LoginAttemptService.JANELA.plusSeconds(1));
        assertFalse(servico.estaBloqueado("aluno@exemplo.com"));
    }

    @Test
    void loginComSucessoLimpaAsFalhas() {
        LoginAttemptService servico = new LoginAttemptService(new RelogioMutavel());
        for (int i = 0; i < LoginAttemptService.MAXIMO_FALHAS; i++) {
            servico.registrarFalha("aluno@exemplo.com");
        }
        servico.limpar("aluno@exemplo.com");
        assertFalse(servico.estaBloqueado("aluno@exemplo.com"));
    }

    @Test
    void falhasAntigasNaoSomamComAsNovas() {
        RelogioMutavel relogio = new RelogioMutavel();
        LoginAttemptService servico = new LoginAttemptService(relogio);
        for (int i = 0; i < LoginAttemptService.MAXIMO_FALHAS - 1; i++) {
            servico.registrarFalha("aluno@exemplo.com");
        }
        relogio.avancar(LoginAttemptService.JANELA.plusSeconds(1));
        servico.registrarFalha("aluno@exemplo.com");
        assertFalse(servico.estaBloqueado("aluno@exemplo.com"));
    }
}
