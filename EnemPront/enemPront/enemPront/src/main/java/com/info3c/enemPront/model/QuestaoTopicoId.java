package com.info3c.enemPront.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class QuestaoTopicoId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "ID_Topico")
    private Long topicoId;

    @Column(name = "ID_Questao")
    private Long questaoId;

    public QuestaoTopicoId() {
    }

    public QuestaoTopicoId(Long topicoId, Long questaoId) {
        this.topicoId = topicoId;
        this.questaoId = questaoId;
    }

    public Long getTopicoId() {
        return topicoId;
    }

    public void setTopicoId(Long topicoId) {
        this.topicoId = topicoId;
    }

    public Long getQuestaoId() {
        return questaoId;
    }

    public void setQuestaoId(Long questaoId) {
        this.questaoId = questaoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuestaoTopicoId that = (QuestaoTopicoId) o;
        return Objects.equals(topicoId, that.topicoId) && Objects.equals(questaoId, that.questaoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topicoId, questaoId);
    }
}
