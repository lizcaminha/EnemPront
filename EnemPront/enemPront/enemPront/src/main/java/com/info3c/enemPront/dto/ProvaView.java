package com.info3c.enemPront.dto;

import java.util.List;

public record ProvaView(List<QuestaoView> questoes, long restanteSegundos, int total) {
}
