package com.info3c.enemPront.service;

import com.info3c.enemPront.dto.CadastroForm;
import com.info3c.enemPront.dto.PerfilForm;
import com.info3c.enemPront.exception.EmailJaCadastradoException;
import com.info3c.enemPront.exception.RecursoNaoEncontradoException;
import com.info3c.enemPront.model.Usuario;
import com.info3c.enemPront.repository.UsuarioRepository;
import com.info3c.enemPront.security.AdminCredenciais;
import com.info3c.enemPront.util.Textos;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;
    private final AdminCredenciais admin;

    public UsuarioService(UsuarioRepository usuarios, PasswordEncoder encoder, AdminCredenciais admin) {
        this.usuarios = usuarios;
        this.encoder = encoder;
        this.admin = admin;
    }

    @Transactional
    public Usuario cadastrar(CadastroForm form) {
        String email = Textos.normalizarEmail(form.getEmail());
        if (admin.ehAdmin(email) || usuarios.existsByEmail(email)) {
            throw new EmailJaCadastradoException();
        }
        Usuario usuario = new Usuario();
        usuario.setNome(form.getNome().trim());
        usuario.setEmail(email);
        usuario.setSenha(encoder.encode(form.getSenha()));
        usuario.setDtNascimento(form.getDtNascimento());
        usuario.setStatus(Usuario.ATIVO);
        try {
            return usuarios.saveAndFlush(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new EmailJaCadastradoException();
        }
    }

    @Transactional(readOnly = true)
    public Usuario buscar(Long id) {
        return usuarios.findById(id).orElseThrow(RecursoNaoEncontradoException::new);
    }

    @Transactional
    public void atualizarPerfil(Long id, PerfilForm form) {
        Usuario usuario = buscar(id);
        usuario.setNome(form.getNome().trim());
        usuario.setDtNascimento(form.getDtNascimento());
    }

    @Transactional
    public void alterarStatus(Long id, boolean ativo) {
        Usuario usuario = buscar(id);
        usuario.setStatus(ativo ? Usuario.ATIVO : Usuario.INATIVO);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return usuarios.findAll(Sort.by("nome").ascending());
    }
}
