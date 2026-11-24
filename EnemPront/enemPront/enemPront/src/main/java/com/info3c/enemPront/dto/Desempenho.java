package com.info3c.enemPront.dto;

import java.util.List;

public record Desempenho(
        long total,
        long acertos,
        long erros,
        int taxa,
        int diasSeguidos,
        int maiorOfensiva,
        long questoesHoje,
        long recordeDiario,
        int progressoOfensiva,
        List<LinhaDesempenho> porArea,
        List<LinhaDesempenho> topicosFracos) {
}
