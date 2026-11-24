package com.info3c.enemPront.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OfensivaTest {

    private static final LocalDate HOJE = LocalDate.of(2026, 11, 15);

    @Test
    void semTentativasTudoZerado() {
        Ofensiva.Resultado r = Ofensiva.calcular(Map.of(), HOJE);
        assertEquals(0, r.diasSeguidos());
        assertEquals(0, r.maiorOfensiva());
        assertEquals(0, r.questoesHoje());
        assertEquals(0, r.recordeDiario());
    }

    @Test
    void contaDiasConsecutivosIncluindoHoje() {
        Map<LocalDate, Long> dias = new HashMap<>();
        dias.put(HOJE, 4L);
        dias.put(HOJE.minusDays(1), 2L);
        dias.put(HOJE.minusDays(2), 9L);
        Ofensiva.Resultado r = Ofensiva.calcular(dias, HOJE);
        assertEquals(3, r.diasSeguidos());
        assertEquals(3, r.maiorOfensiva());
        assertEquals(4, r.questoesHoje());
        assertEquals(9, r.recordeDiario());
    }

    @Test
    void ofensivaSeguraAteOntemSeHojeAindaNaoHouveTentativa() {
        Map<LocalDate, Long> dias = new HashMap<>();
        dias.put(HOJE.minusDays(1), 1L);
        dias.put(HOJE.minusDays(2), 1L);
        Ofensiva.Resultado r = Ofensiva.calcular(dias, HOJE);
        assertEquals(2, r.diasSeguidos());
        assertEquals(0, r.questoesHoje());
    }

    @Test
    void lacunaQuebraAOfensivaAtualMasPreservaAMaior() {
        Map<LocalDate, Long> dias = new HashMap<>();
        dias.put(HOJE, 1L);
        dias.put(HOJE.minusDays(3), 1L);
        dias.put(HOJE.minusDays(4), 1L);
        dias.put(HOJE.minusDays(5), 1L);
        Ofensiva.Resultado r = Ofensiva.calcular(dias, HOJE);
        assertEquals(1, r.diasSeguidos());
        assertEquals(3, r.maiorOfensiva());
    }

    @Test
    void ofensivaZeradaQuandoUltimaAtividadeFoiHaMaisDeUmDia() {
        Map<LocalDate, Long> dias = new HashMap<>();
        dias.put(HOJE.minusDays(2), 5L);
        Ofensiva.Resultado r = Ofensiva.calcular(dias, HOJE);
        assertEquals(0, r.diasSeguidos());
        assertEquals(1, r.maiorOfensiva());
    }
}
