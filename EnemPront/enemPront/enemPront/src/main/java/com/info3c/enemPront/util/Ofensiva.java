package com.info3c.enemPront.util;

import java.time.LocalDate;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

public final class Ofensiva {

    public record Resultado(int diasSeguidos, int maiorOfensiva, long questoesHoje, long recordeDiario) {
    }

    private Ofensiva() {
    }

    public static Resultado calcular(Map<LocalDate, Long> tentativasPorDia, LocalDate hoje) {
        NavigableSet<LocalDate> dias = new TreeSet<>();
        long recorde = 0;
        for (Map.Entry<LocalDate, Long> entrada : tentativasPorDia.entrySet()) {
            Long quantidade = entrada.getValue();
            if (quantidade != null && quantidade > 0 && !entrada.getKey().isAfter(hoje)) {
                dias.add(entrada.getKey());
                recorde = Math.max(recorde, quantidade);
            }
        }

        int maior = 0;
        int sequencia = 0;
        LocalDate anterior = null;
        for (LocalDate dia : dias) {
            sequencia = anterior != null && anterior.plusDays(1).equals(dia) ? sequencia + 1 : 1;
            maior = Math.max(maior, sequencia);
            anterior = dia;
        }

        LocalDate cursor = dias.contains(hoje) ? hoje : hoje.minusDays(1);
        int atual = 0;
        while (dias.contains(cursor)) {
            atual++;
            cursor = cursor.minusDays(1);
        }

        long hojeTotal = tentativasPorDia.getOrDefault(hoje, 0L);
        return new Resultado(atual, maior, hojeTotal, recorde);
    }
}
