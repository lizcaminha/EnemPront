package com.info3c.enemPront.repository;

import com.info3c.enemPront.model.TentativaResposta;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TentativaRespostaRepository extends JpaRepository<TentativaResposta, Long> {

    @Query("select count(t) from TentativaResposta t where t.usuario.id = :usuarioId")
    long contarPorUsuario(@Param("usuarioId") Long usuarioId);

    @Query("select count(t) from TentativaResposta t "
            + "where t.usuario.id = :usuarioId and t.tentativaCorreta = 'S'")
    long contarAcertosPorUsuario(@Param("usuarioId") Long usuarioId);

    @Query("select t.dtTentativa, count(t) from TentativaResposta t "
            + "where t.usuario.id = :usuarioId group by t.dtTentativa")
    List<Object[]> contarPorDia(@Param("usuarioId") Long usuarioId);

    @Query("select t.questao.id, count(t), sum(case when t.tentativaCorreta = 'S' then 1 else 0 end) "
            + "from TentativaResposta t where t.usuario.id = :usuarioId group by t.questao.id")
    List<Object[]> resumoPorQuestao(@Param("usuarioId") Long usuarioId);

    @Query("select t.questao.id, t.alternativaQuestao from TentativaResposta t "
            + "where t.usuario.id = :usuarioId and t.questao.id in :ids order by t.id desc")
    List<Object[]> ultimasRespostas(@Param("usuarioId") Long usuarioId, @Param("ids") Collection<Long> ids);

    @Query("select count(t) from TentativaResposta t where t.questao.id = :questaoId")
    long contarPorQuestao(@Param("questaoId") Long questaoId);

    @Query("select t.usuario.id, count(t), sum(case when t.tentativaCorreta = 'S' then 1 else 0 end), "
            + "max(t.dtTentativa) from TentativaResposta t group by t.usuario.id")
    List<Object[]> resumoPorUsuario();
}
