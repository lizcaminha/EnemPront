package br.com.info3c.enemPront.model;

import jakarta.persistence.*;

@Entity
public class Area_Conhecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID_Area_Conhecimento;
    private Integer Ano_aplicacao;
    private String Tipo_aplicacao;

    public Long getID_Area_Conhecimento(){
        return ID_Area_Conhecimento;
    }
    public void setID_Area_Conhecimento(Long ID_Area_Conhecimento){
        this.ID_Area_Conhecimento = ID_Area_Conhecimento;
    }
    public Integer getAno_aplicacao(){
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
