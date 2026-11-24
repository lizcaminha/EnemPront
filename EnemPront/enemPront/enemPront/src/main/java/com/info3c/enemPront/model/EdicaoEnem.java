package com.info3c.enemPront.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Edicao_Enem")
public class EdicaoEnem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Edicao_Enem")
    private Long id;

    @Column(name = "Ano_aplicacao", nullable = false)
    private Integer anoAplicacao;

    @Column(name = "Tipo_aplicacao", nullable = false, length = 3)
    private String tipoAplicacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnoAplicacao() {
        return anoAplicacao;
    }

    public void setAnoAplicacao(Integer anoAplicacao) {
        this.anoAplicacao = anoAplicacao;
    }

    public String getTipoAplicacao() {
        return tipoAplicacao;
    }

    public void setTipoAplicacao(String tipoAplicacao) {
        this.tipoAplicacao = tipoAplicacao;
    }
}
