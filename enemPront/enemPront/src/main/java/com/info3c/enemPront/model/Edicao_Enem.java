package br.com.info3c.enemPront.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Edicao_Enem")
public class Edicao_Enem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID_Edicao_Enem;
    private Integer Ano_aplicacao;
    private String Tipo_aplicacao;

    public Long getID_Edicao_Enem(){
        return ID_Edicao_Enem;
    }
    public void setID_Edicao_Enem(Long ID_Edicao_Enem){
        this.ID_Edicao_Enem = ID_Edicao_Enem;
    }
    public Integer getAno_Aplicacao(){
        return Ano_aplicacao;
    }
    public void setAno_aplicacao(Integer Ano_aplicacao){
        this.Ano_aplicacao = Ano_aplicacao;
    }
    public String getTipo_aplicacao(){
        return Tipo_aplicacao;
    }
    public void setTipo_aplicacao(String Tipo_aplicacao){
        this.Tipo_aplicacao = Tipo_aplicacao;
    }
}
