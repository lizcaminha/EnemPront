package com.info3c.enemPront.dto;

public record UsuarioRelatorio(
        Long id,
        String nome,
        String email,
        String nascimento,
        boolean ativo,
        long total,
        long acertos,
        int taxa,
        String ultimaAtividade) {
}
