package com.info3c.enemPront.repository;

import com.info3c.enemPront.model.Topico;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    List<Topico> findAllByOrderByNomeAsc();

    @Query("select count(t) from Topico t where t.area.id = :areaId")
    long contarPorArea(@Param("areaId") Long areaId);
}
