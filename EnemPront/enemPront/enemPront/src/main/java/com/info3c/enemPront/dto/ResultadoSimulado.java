package com.info3c.enemPront.dto;

import java.io.Serializable;
import java.util.List;

public record ResultadoSimulado(
        int total,
        int respondidas,
        int acertos,
        int erros,
        int emBranco,
        int aproveitamento,
        String tempo,
        List<QuestaoView> revisao) implements Serializable {
}
