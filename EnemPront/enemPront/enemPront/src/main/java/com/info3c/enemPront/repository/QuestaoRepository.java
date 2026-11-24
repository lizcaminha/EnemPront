package com.info3c.enemPront.repository;

import com.info3c.enemPront.model.Questao;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestaoRepository extends JpaRepository<Questao, Long> {

    @Query("select q.id from Questao q")
    List<Long> findAllIds();

    @Query("select q.id from Questao q where exists "
            + "(select 1 from QuestaoTopico qt join qt.topico tp "
            + "where qt.id.questaoId = q.id and tp.area.id = :areaId)")
    List<Long> findIdsByArea(@Param("areaId") Long areaId);

    @Query("select count(q) from Questao q where q.edicaoEnem.id = :edicaoId")
    long contarPorEdicao(@Param("edicaoId") Long edicaoId);

    List<Questao> findByIdIn(Collection<Long> ids);
}
