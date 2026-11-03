package com.info3c.enemPront.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Questao")
public class Questao{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Questao")
    private Long IdQuestao;
    @Column(name = "Enunciado")
    private String Enunciado;
    @Column(name = "Alternativa_A", nullable = false)
    private String AlternativaA;
    @Column(name = "Alternativa_B", nullable = false)
    private String AlternativaB;
    @Column(name = "Alternativa_C", nullable = false)
    private String AlternativaC;
    @Column(name = "Alternativa_D", nullable = false)
    private String AlternativaD;
    @Column(name = "Alternativa_E", nullable = false)
    private String AlternativaE;
    @Column(name = "Alternativa_correta", nullable = false)
    private String AlternativaCorreta;

    @ManyToOne
    @JoinColumn(name = "ID_Edicao_Enem", nullable = false)
    private EdicaoEnem IdEdicaoEnem;

    public Long getIdQuestao(){
        return IdQuestao;
    }
    public void setIdQuestao(Long IdQuestao){
        this.IdQuestao = IdQuestao;
    }
    public String getEnunciado(){
        return Enunciado;
    }
    public void setEnunciado(String Enunciado){
        this.Enunciado = Enunciado;
    }
    public String getAlternativaA(){
        return AlternativaA;
    }
    public void setAlternativaA(String AlternativaA){
        this.AlternativaA = AlternativaA;
    }
    public String getAlternativaB(){
        return AlternativaB;
    }
    public void setAlternativa_(String AlternativaB){
        this.AlternativaB = AlternativaB;
    }
    public String getAlternativa_C(){
        return AlternativaC;
    }
    public void setAlternativa_C(String AlternativaC){
        this.AlternativaC = AlternativaC;
    }
    public String getAlternativaD(){
        return AlternativaD;
    }
    public void setAlternativaD(String AlternativaD){
        this.AlternativaD = AlternativaD;
    }
    public String getAlternativaE(){
        return AlternativaE;
    }
    public void setAlternativaE(String AlternativaE){
        this.AlternativaE = AlternativaE;
    }
    public String getAlternativaCorreta(){
        return AlternativaCorreta;
    }
    public void setAlternativaCorreta(String AlternativaCorreta){
        this.AlternativaCorreta = AlternativaCorreta;
    }
    public EdicaoEnem getIdEdicaoEnem(){
        return IdEdicaoEnem;
    }
    public void setIdEdicaoEnem(){
        this.IdEdicaoEnem = IdEdicaoEnem;
    }
}