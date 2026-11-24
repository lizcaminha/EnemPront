package com.info3c.enemPront.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "Questao_Topico")
public class QuestaoTopico {

    @EmbeddedId
    private QuestaoTopicoId id = new QuestaoTopicoId();

    @ManyToOne(optional = false)
    @MapsId("topicoId")
    @JoinColumn(name = "ID_Topico")
    private Topico topico;

    @ManyToOne(optional = false)
    @MapsId("questaoId")
    @JoinColumn(name = "ID_Questao")
    private Questao questao;

    @Column(name = "Dificuldade", nullable = false, length = 1)
    private String dificuldade;

    public QuestaoTopico() {
    }

    public QuestaoTopico(Questao questao, Topico topico, String dificuldade) {
        this.id = new QuestaoTopicoId(topico.getId(), questao.getId());
        this.questao = questao;
        this.topico = topico;
        this.dificuldade = dificuldade;
    }

    public QuestaoTopicoId getId() {
        return id;
    }

    public void setId(QuestaoTopicoId id) {
        this.id = id;
    }

    public Topico getTopico() {
        return topico;
    }

    public void setTopico(Topico topico) {
        this.topico = topico;
    }

    public Questao getQuestao() {
        return questao;
    }

    public void setQuestao(Questao questao) {
        this.questao = questao;
    }

    public String getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }
}
