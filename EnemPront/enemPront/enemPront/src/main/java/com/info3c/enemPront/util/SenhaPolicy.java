package com.info3c.enemPront.util;

import java.util.ArrayList;
import java.util.List;

public final class SenhaPolicy {

    public static final int TAMANHO_MINIMO = 8;
    public static final int TAMANHO_MAXIMO = 64;

    private SenhaPolicy() {
    }

    public static List<String> violacoes(String senha) {
        List<String> problemas = new ArrayList<>();
        if (senha == null || senha.length() < TAMANHO_MINIMO) {
            problemas.add("A senha deve ter no mínimo " + TAMANHO_MINIMO + " caracteres");
        }
        if (senha != null && senha.length() > TAMANHO_MAXIMO) {
            problemas.add("A senha deve ter no máximo " + TAMANHO_MAXIMO + " caracteres");
        }
        if (senha == null || senha.chars().noneMatch(c -> c >= '0' && c <= '9')) {
            problemas.add("A senha deve conter ao menos 1 número");
        }
        if (senha == null || senha.codePoints().noneMatch(SenhaPolicy::ehEspecial)) {
            problemas.add("A senha deve conter ao menos 1 caractere especial");
        }
        return problemas;
    }

    private static boolean ehEspecial(int codePoint) {
        return !Character.isLetterOrDigit(codePoint) && !Character.isWhitespace(codePoint);
    }
}
