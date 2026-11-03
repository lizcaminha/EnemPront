package br.com.info3c.enemPront.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Questao_Topico")
public class QuestaoTopico {

    @EmbeddedId
    private QuestaoTopicoId id = new QuestaoTopicoId();

    @ManyToOne
    @MapsId("IdTopico")
    @JoinColumn(name = "ID_Topico")
    private Topico IdTopico;

    @ManyToOne
    @MapsId("IdQuestao")
    @JoinColumn(name = "ID_Questao")
    private Questao IdQuestao;

    @Column(name = "Dificuldade")
    private String Dificuldade;

    public QuestaoTopico() {}

    public QuestaoTopicoId getId() {
        return id;
    }
    public void setId(QuestaoTopicoId id) {
        this.id = id;
    }
    public Topico getIdTopico() {
        return IdTopico;
    }
    public void setIdTopico(Topico IdTopico) {
        this.IdTopico = IdTopico;
    }
    public Questao getIdQuestao() {
        return IdQuestao;
    }
    public void setIdQuestao(Questao IdQuestao) {
        this.IdQuestao = IdQuestao;
    }
    public String getDificuldade() {
        return Dificuldade;
    }
    public void setDificuldade(String Dificuldade) {
        this.Dificuldade = Dificuldade;
    }
}
