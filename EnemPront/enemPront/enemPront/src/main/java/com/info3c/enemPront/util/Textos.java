package com.info3c.enemPront.util;

import com.info3c.enemPront.exception.RegraNegocioException;
import java.util.Locale;

public final class Textos {

    private static final String ALTERNATIVAS_VALIDAS = "ABCDE";

    private Textos() {
    }

    public static String normalizarEmail(String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }

    public static String alternativa(String letra) {
        if (letra == null) {
            throw new RegraNegocioException("Alternativa inválida");
        }
        String normalizada = letra.trim().toUpperCase(Locale.ROOT);
        if (normalizada.length() != 1 || ALTERNATIVAS_VALIDAS.indexOf(normalizada.charAt(0)) < 0) {
            throw new RegraNegocioException("Alternativa inválida");
        }
        return normalizada;
    }

    public static String escaparLike(String texto) {
        return texto.replace("!", "!!").replace("%", "!%").replace("_", "!_");
    }

    public static String resumir(String texto, int maximo) {
        if (texto == null || texto.length() <= maximo) {
            return texto;
        }
        return texto.substring(0, maximo).stripTrailing() + "…";
    }

    public static String duracao(long segundos) {
        long total = Math.max(0, segundos);
        long horas = total / 3600;
        long minutos = (total % 3600) / 60;
        long resto = total % 60;
        if (horas > 0) {
            return String.format(Locale.ROOT, "%d:%02d:%02d", horas, minutos, resto);
        }
        return String.format(Locale.ROOT, "%d:%02d", minutos, resto);
    }

    public static String horasMinutos(int minutos) {
        return String.format(Locale.ROOT, "%d:%02d", minutos / 60, minutos % 60);
    }

    public static String csv(String valor) {
        String texto = valor == null ? "" : valor;
        if (!texto.isEmpty() && "=+-@\t\r".indexOf(texto.charAt(0)) >= 0) {
            texto = "'" + texto;
        }
        return "\"" + texto.replace("\"", "\"\"") + "\"";
    }
}
