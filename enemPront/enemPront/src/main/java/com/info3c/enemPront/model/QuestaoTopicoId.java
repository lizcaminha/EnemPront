package com.info3c.enemPront.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class QuestaoTopicoId implements Serializable {

    @Column(name = "ID_Topico")
    private Long IdTopico;

    @Column(name = "ID_Questao")
    private Long IdQuestao;

    public QuestaoTopicoId() {}

    public QuestaoTopicoId(Long IdTopico, Long IdQuestao) {
        this.IdTopico = IdTopico;
        this.IdQuestao = IdQuestao;
    }

    public Long getIdTopico() {
        return IdTopico;
    }
    public void setIdTopico(Long IdTopico) {
        this.IdTopico = IdTopico;
    }
    public Long getIdQuestao() {
        return IdQuestao;
    }
    public void setIdQuestao(Long IdQuestao) {
        this.IdQuestao = IdQuestao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestaoTopicoId that = (QuestaoTopicoId) o;
        return Objects.equals(IdTopico, that.IdTopico) && Objects.equals(IdQuestao, that.IdQuestao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IdTopico, IdQuestao);
    }
}
