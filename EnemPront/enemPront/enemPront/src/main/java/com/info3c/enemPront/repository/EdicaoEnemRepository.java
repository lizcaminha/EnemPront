package com.info3c.enemPront.repository;

import com.info3c.enemPront.model.EdicaoEnem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EdicaoEnemRepository extends JpaRepository<EdicaoEnem, Long> {

    List<EdicaoEnem> findAllByOrderByAnoAplicacaoDescTipoAplicacaoAsc();

    boolean existsByAnoAplicacaoAndTipoAplicacao(Integer anoAplicacao, String tipoAplicacao);
}
