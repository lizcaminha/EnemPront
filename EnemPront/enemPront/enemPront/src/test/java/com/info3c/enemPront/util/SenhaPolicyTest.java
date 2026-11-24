package com.info3c.enemPront.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SenhaPolicyTest {

    @Test
    void aceitaSenhaComOitoCaracteresNumeroEEspecial() {
        assertTrue(SenhaPolicy.violacoes("Enem@2026").isEmpty());
    }

    @Test
    void rejeitaSenhaCurta() {
        assertFalse(SenhaPolicy.violacoes("A@1b").isEmpty());
    }

    @Test
    void rejeitaSenhaSemNumero() {
        assertEquals(1, SenhaPolicy.violacoes("Senha@forte").size());
    }

    @Test
    void rejeitaSenhaSemCaractereEspecial() {
        assertEquals(1, SenhaPolicy.violacoes("Senha12345").size());
    }

    @Test
    void espacoNaoContaComoCaractereEspecial() {
        assertFalse(SenhaPolicy.violacoes("Senha 12345").isEmpty());
    }

    @Test
    void rejeitaSenhaNulaOuMuitoLonga() {
        assertFalse(SenhaPolicy.violacoes(null).isEmpty());
        assertFalse(SenhaPolicy.violacoes("a1@" + "x".repeat(70)).isEmpty());
    }
}
