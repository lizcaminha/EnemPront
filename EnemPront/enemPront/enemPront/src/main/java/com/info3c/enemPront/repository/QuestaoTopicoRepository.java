package com.info3c.enemPront.repository;

import com.info3c.enemPront.model.QuestaoTopico;
import com.info3c.enemPront.model.QuestaoTopicoId;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestaoTopicoRepository extends JpaRepository<QuestaoTopico, QuestaoTopicoId> {

    @Query("select qt from QuestaoTopico qt join fetch qt.topico t join fetch t.area "
            + "where qt.id.questaoId in :ids")
    List<QuestaoTopico> buscarComTopico(@Param("ids") Collection<Long> ids);

    @Query("select qt from QuestaoTopico qt where qt.id.questaoId = :questaoId")
    List<QuestaoTopico> porQuestao(@Param("questaoId") Long questaoId);

    @Query("select count(qt) from QuestaoTopico qt where qt.id.topicoId = :topicoId")
    long contarPorTopico(@Param("topicoId") Long topicoId);

    @Modifying
    @Query("delete from QuestaoTopico qt where qt.id.questaoId = :questaoId")
    void excluirPorQuestao(@Param("questaoId") Long questaoId);

    @Query("select qt.id.questaoId, tp.id, tp.nome, a.id, a.nome "
            + "from QuestaoTopico qt join qt.topico tp join tp.area a "
            + "where qt.id.questaoId in :ids")
    List<Object[]> topicosDasQuestoes(@Param("ids") Collection<Long> ids);
}
