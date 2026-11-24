package com.info3c.enemPront.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Questao")
public class Questao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Questao")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_Edicao_Enem", nullable = false)
    private EdicaoEnem edicaoEnem;

    @Column(name = "Enunciado", nullable = false, length = 5000)
    private String enunciado;

    @Column(name = "Alternativa_A", nullable = false, length = 200)
    private String alternativaA;

    @Column(name = "Alternativa_B", nullable = false, length = 200)
    private String alternativaB;

    @Column(name = "Alternativa_C", nullable = false, length = 200)
    private String alternativaC;

    @Column(name = "Alternativa_D", nullable = false, length = 200)
    private String alternativaD;

    @Column(name = "Alternativa_E", nullable = false, length = 200)
    private String alternativaE;

    @Column(name = "Alternativa_correta", nullable = false, length = 1)
    private String alternativaCorreta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EdicaoEnem getEdicaoEnem() {
        return edicaoEnem;
    }

    public void setEdicaoEnem(EdicaoEnem edicaoEnem) {
        this.edicaoEnem = edicaoEnem;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getAlternativaA() {
        return alternativaA;
    }

    public void setAlternativaA(String alternativaA) {
        this.alternativaA = alternativaA;
    }

    public String getAlternativaB() {
        return alternativaB;
    }

    public void setAlternativaB(String alternativaB) {
        this.alternativaB = alternativaB;
    }

    public String getAlternativaC() {
        return alternativaC;
    }

    public void setAlternativaC(String alternativaC) {
        this.alternativaC = alternativaC;
    }

    public String getAlternativaD() {
        return alternativaD;
    }

    public void setAlternativaD(String alternativaD) {
        this.alternativaD = alternativaD;
    }

    public String getAlternativaE() {
        return alternativaE;
    }

    public void setAlternativaE(String alternativaE) {
        this.alternativaE = alternativaE;
    }

    public String getAlternativaCorreta() {
        return alternativaCorreta;
    }

    public void setAlternativaCorreta(String alternativaCorreta) {
        this.alternativaCorreta = alternativaCorreta;
    }

    public String textoDaAlternativa(String letra) {
        return switch (letra) {
            case "A" -> alternativaA;
            case "B" -> alternativaB;
            case "C" -> alternativaC;
            case "D" -> alternativaD;
            case "E" -> alternativaE;
            default -> throw new IllegalArgumentException("Alternativa inexistente: " + letra);
        };
    }
}
