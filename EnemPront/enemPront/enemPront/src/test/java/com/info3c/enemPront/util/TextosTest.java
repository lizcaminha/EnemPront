package com.info3c.enemPront.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.info3c.enemPront.exception.RegraNegocioException;
import org.junit.jupiter.api.Test;

class TextosTest {

    @Test
    void normalizaEmail() {
        assertEquals("aluno@exemplo.com", Textos.normalizarEmail("  Aluno@Exemplo.COM "));
        assertEquals("", Textos.normalizarEmail(null));
    }

    @Test
    void alternativaAceitaApenasDeAAteE() {
        assertEquals("C", Textos.alternativa(" c "));
        assertThrows(RegraNegocioException.class, () -> Textos.alternativa("F"));
        assertThrows(RegraNegocioException.class, () -> Textos.alternativa("AB"));
        assertThrows(RegraNegocioException.class, () -> Textos.alternativa(null));
    }

    @Test
    void escapaCuringasDoLike() {
        assertEquals("100!% !_x !!", Textos.escaparLike("100% _x !"));
    }

    @Test
    void formataDuracoes() {
        assertEquals("2:05", Textos.duracao(125));
        assertEquals("1:01:01", Textos.duracao(3661));
        assertEquals("0:00", Textos.duracao(-4));
        assertEquals("2:15", Textos.horasMinutos(135));
    }

    @Test
    void csvNeutralizaFormulasEEscapaAspas() {
        assertEquals("\"'=1+1\"", Textos.csv("=1+1"));
        assertEquals("\"diz \"\"oi\"\"\"", Textos.csv("diz \"oi\""));
    }
}
