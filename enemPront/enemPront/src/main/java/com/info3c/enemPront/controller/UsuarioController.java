package com.info3c.enemPront.controller;

import com.info3c.enemPront.DTO.UsuarioDTO;
import com.info3c.enemPront.model.Usuario;
import com.info3c.enemPront.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioController(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<UsuarioDTO> listarUsuarios(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map( p -> new UsuarioDTO(p.getNome(), p.getEmail(), p.getSenha()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public UsuarioDTO adicionarUsuario(@RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return new UsuarioDTO(salvo.getNome(), salvo.getEmail);
    }

    @PutMapping
    public Usuario atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado){
        Usuario usuario = usuarioRepository.findById(Math.toIntExact(id)).orElseThrow();
        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        usuario.setSenha(usuarioAtualizado.getSenha());
        return usuarioRepository.save(usuario);
    }

    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Integer id ){
        usuarioRepository.deleteById(id);
    }
}