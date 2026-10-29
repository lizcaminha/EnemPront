package br.com.info3c.enemPront.model;

import  jakarta.persistence.*;

@Entity

public class Tentativa_resposta{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID_Tentativa_resposta;
    private String Alternativa_Questao;

    public Long getID_Tentativa_Resposta(){
        return ID_Tentativa_resposta;
    }
    public void setID_Tentativa_resposta(Long ID_Tentativa_resposta){
        this.ID_Tentativa_resposta = ID_Tentativa_resposta;
    }
    public String getAlternativa_Questao(){
        return Alternativa_Questao;
    }
    public void setAlternativa_Questao(String Alternativa_Questao){
        this.Alternativa_Questao = Alternativa_Questao;
    }
}