package com.info3c.enemPront.security;

import com.info3c.enemPront.model.Usuario;
import com.info3c.enemPront.repository.UsuarioRepository;
import com.info3c.enemPront.util.Textos;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ContaUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarios;
    private final AdminCredenciais admin;
    private final LoginAttemptService tentativas;

    public ContaUserDetailsService(UsuarioRepository usuarios, AdminCredenciais admin, LoginAttemptService tentativas) {
        this.usuarios = usuarios;
        this.admin = admin;
        this.tentativas = tentativas;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String email = Textos.normalizarEmail(username);
        boolean bloqueada = tentativas.estaBloqueado(email);
        if (admin.ehAdmin(email)) {
            return new ContaUsuario(null, admin.email(), admin.nome(), admin.senhaHash(), true, bloqueada);
        }
        Usuario usuario = usuarios.findByEmail(email)
                .filter(Usuario::isAtivo)
                .orElseThrow(() -> new UsernameNotFoundException("Credenciais inválidas"));
        return new ContaUsuario(usuario.getId(), usuario.getEmail(), usuario.getNome(), usuario.getSenha(), false, bloqueada);
    }
}
