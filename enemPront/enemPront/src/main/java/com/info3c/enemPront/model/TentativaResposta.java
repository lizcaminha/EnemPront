package br.com.info3c.enemPront.model;

import  jakarta.persistence.*;

@Entity

public class TentativaResposta{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Tentativa_resposta")
    private Long IdTentativaResposta;
    @Column(name = "Alternativa_Questao", nullable = false)
    private String AlternativaQuestao;

    public Long getIdTentativaResposta(){
        return IdTentativaResposta;
    }
    public void setIdTentativaResposta(Long IdTentativaResposta){
        this.IdTentativaResposta = IdTentativaResposta;
    }
    public String getAlternativaQuestao(){
        return AlternativaQuestao;
    }
    public void setAlternativaQuestao(String AlternativaQuestao){
        this.AlternativaQuestao = AlternativaQuestao;
    }
}