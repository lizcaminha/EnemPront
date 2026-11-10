package com.info3c.enemPront.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.info3c.enemPront.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findByNome(String nome);
    List<Usuario> findByNomeContaining(String nome);
}
