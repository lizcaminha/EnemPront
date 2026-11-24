package com.info3c.enemPront.dto;

import java.io.Serializable;
import java.util.List;

public record QuestaoView(
        Long id,
        List<String> tags,
        String enunciado,
        List<AlternativaView> alternativas,
        boolean respondida,
        boolean acertou,
        String mensagem) implements Serializable {
}
