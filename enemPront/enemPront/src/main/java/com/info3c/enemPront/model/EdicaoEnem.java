package com.info3c.enemPront.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Edicao_Enem")
public class EdicaoEnem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Edicao_Enem", nullable = false)
    private Long IdEdicaoEnem;
    @Column(name = "Ano_aplicacao", nullable = false)
    private Integer AnoAplicacao;
    @Column(name = "Tipo_aplicacao", nullable = false)
    private String TipoAplicacao;

    public Long getIdEdicaoEnem(){
        return IdEdicaoEnem;
    }
    public void setIdEdicaoEnem(Long IdEdicaoEnem){
        this.IdEdicaoEnem = IdEdicaoEnem;
    }
    public Integer getAnoAplicacao(){
        return AnoAplicacao;
    }
    public void setAnoAplicacao(Integer AnoAplicacao){
        this.AnoAplicacao = AnoAplicacao;
    }
    public String getTipoAplicacao(){
        return TipoAplicacao;
    }
    public void setTipoAplicacao(String TipoAplicacao){
        this.TipoAplicacao = TipoAplicacao;
    }
}
