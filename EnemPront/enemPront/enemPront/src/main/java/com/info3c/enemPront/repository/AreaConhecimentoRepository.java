package com.info3c.enemPront.repository;

import com.info3c.enemPront.model.AreaConhecimento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaConhecimentoRepository extends JpaRepository<AreaConhecimento, Long> {

    List<AreaConhecimento> findAllByOrderByNomeAsc();
}
