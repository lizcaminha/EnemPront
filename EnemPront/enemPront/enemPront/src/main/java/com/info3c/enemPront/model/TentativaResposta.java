package com.info3c.enemPront.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "Tentativa_resposta")
public class TentativaResposta {

    public static final String CORRETA = "S";
    public static final String INCORRETA = "N";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Tentativa_resposta")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_Questao", nullable = false)
    private Questao questao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_Usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "Alternativa_Questao", nullable = false, length = 1)
    private String alternativaQuestao;

    @Column(name = "Dt_Tentativa", nullable = false)
    private LocalDate dtTentativa;

    @Column(name = "Tentativa_Correta", nullable = false, length = 1)
    private String tentativaCorreta;

    public TentativaResposta() {
    }

    public TentativaResposta(Usuario usuario, Questao questao, String alternativa, LocalDate data, boolean correta) {
        this.usuario = usuario;
        this.questao = questao;
        this.alternativaQuestao = alternativa;
        this.dtTentativa = data;
        this.tentativaCorreta = correta ? CORRETA : INCORRETA;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Questao getQuestao() {
        return questao;
    }

    public void setQuestao(Questao questao) {
        this.questao = questao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getAlternativaQuestao() {
        return alternativaQuestao;
    }

    public void setAlternativaQuestao(String alternativaQuestao) {
        this.alternativaQuestao = alternativaQuestao;
    }

    public LocalDate getDtTentativa() {
        return dtTentativa;
    }

    public void setDtTentativa(LocalDate dtTentativa) {
        this.dtTentativa = dtTentativa;
    }

    public String getTentativaCorreta() {
        return tentativaCorreta;
    }

    public void setTentativaCorreta(String tentativaCorreta) {
        this.tentativaCorreta = tentativaCorreta;
    }
}
