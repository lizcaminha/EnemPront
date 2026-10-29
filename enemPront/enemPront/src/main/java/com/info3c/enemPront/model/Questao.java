package br.com.info3c.enemPront.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Questao")
public class Questao{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Questao")
    private Long idQuestao;
    @Column(name = "Enunciado")
    private String enunciado;
    @Column(name = "Alternativa_A")
    private String alternativaA;
    private String alternativaB;
    private String alternativaC;
    private String alternativaD;
    private String alternativaE;
    private String alternativaCorreta;

    @ManyToOne
    @JoinColumn(name = "ID_Edicao_Enem", nullable = false)
    private Edicao_Enem edicaoEnem;

    public Long getID_Questao(){
        return ID_Questao;
    }
    public void setID_Questao(Long ID_Questao){
        this.ID_Questao = ID_Questao;
    }
    public String getEnunciado(){
        return Enunciado;
    }
    public void setEnunciado(String Enunciado){
        this.Enunciado = Enunciado;
    }
    public String getAlternativa_A(){
        return Alternativa_A;
    }
    public void setAlternativa_A(String Alternativa_A){
        this.Alternativa_A = Alternativa_A;
    }
    public String getAlternativa_B(){
        return Alternativa_B;
    }
    public void setAlternativa_B(String Alternativa_B){
        this.Alternativa_B = Alternativa_B;
    }
    public String getAlternativa_C(){
        return Alternativa_C;
    }
    public void setAlternativa_C(String Alternativa_C){
        this.Alternativa_C = Alternativa_C;
    }
    public String getAlternativa_D(){
        return Alternativa_D;
    }
    public void setAlternativa_D(String Alternativa_D){
        this.Alternativa_D = Alternativa_D;
    }
    public String getAlternativa_E(){
        return Alternativa_E;
    }
    public void setAlternativa_E(String Alternativa_E){
        this.Alternativa_E = Alternativa_E;
    }
    public String getAlternativa_correta(){
        return Alternativa_correta;
    }
    public void setAlternativa_correta(String Alternativa_correta){
        this.Alternativa_correta = Alternativa_correta
    }
    public Edicao_Enem getEdicaoEnem(){
        return edicaoEnem;
    }
    public void setEdicaoEnem(){
        this.edicaoEnem = edicaoEnem;
    }
}