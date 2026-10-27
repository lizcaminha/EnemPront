package br.com.info3c.enemPront.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Edicao_Enem")
public class EdicaoEnem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Edicao_Enem")
    private Long ID_Edicao_Enem;

    @Column(name = "ano_aplicacao")
    private Integer anoAplicacao;

    @Column(name = "tipo_aplicacao")
    private String tipoAplicacao;


    public Long getID_Edicao_Enem() {
        return ID_Edicao_Enem;
    }

    public void setID_Edicao_Enem(Long ID_Edicao_Enem) {
        this.ID_Edicao_Enem = ID_Edicao_Enem;
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
