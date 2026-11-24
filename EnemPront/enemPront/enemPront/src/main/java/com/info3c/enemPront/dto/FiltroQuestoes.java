package com.info3c.enemPront.dto;

import java.util.Locale;
import java.util.Set;

public class FiltroQuestoes {

    private static final Set<String> NIVEIS = Set.of("A", "M", "B");
    private static final int TAMANHO_MAXIMO_TEXTO = 100;

    private Long area;
    private Long topico;
    private Integer ano;
    private String dificuldade;
    private String incidencia;
    private String q;
    private int pagina;

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public Long getTopico() {
        return topico;
    }

    public void setTopico(Long topico) {
        this.topico = topico;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(String dificuldade) {
        this.dificuldade = nivelValido(dificuldade);
    }

    public String getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(String incidencia) {
        this.incidencia = nivelValido(incidencia);
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        if (q == null || q.isBlank()) {
            this.q = null;
            return;
        }
        String limpo = q.trim();
        this.q = limpo.length() > TAMANHO_MAXIMO_TEXTO ? limpo.substring(0, TAMANHO_MAXIMO_TEXTO) : limpo;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = Math.max(0, pagina);
    }

    private static String nivelValido(String valor) {
        if (valor == null) {
            return null;
        }
        String normalizado = valor.trim().toUpperCase(Locale.ROOT);
        return NIVEIS.contains(normalizado) ? normalizado : null;
    }
}
