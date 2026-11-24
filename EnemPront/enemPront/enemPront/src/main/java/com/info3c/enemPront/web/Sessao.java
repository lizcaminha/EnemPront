package com.info3c.enemPront.web;

import com.info3c.enemPront.security.ContaUsuario;

public final class Sessao {

    private Sessao() {
    }

    public static Long alunoId(ContaUsuario conta) {
        return conta != null && !conta.isAdmin() ? conta.getId() : null;
    }
}
