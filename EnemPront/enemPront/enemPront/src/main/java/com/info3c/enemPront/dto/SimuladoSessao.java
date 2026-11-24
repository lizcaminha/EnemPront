package com.info3c.enemPront.dto;

import java.io.Serializable;
import java.util.List;

public record SimuladoSessao(List<Long> ids, long inicioMillis, int duracaoMinutos) implements Serializable {

    public long fimMillis() {
        return inicioMillis + duracaoMinutos * 60_000L;
    }
}
