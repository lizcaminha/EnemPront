package com.info3c.enemPront.security;

import com.info3c.enemPront.config.EnemProntProperties;
import com.info3c.enemPront.util.Textos;
import java.security.SecureRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminCredenciais {

    private static final Logger LOG = LoggerFactory.getLogger(AdminCredenciais.class);
    private static final String ALFABETO = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz23456789";
    private static final int TAMANHO_SENHA_GERADA = 16;

    private final String email;
    private final String nome;
    private final String senhaHash;

    public AdminCredenciais(EnemProntProperties propriedades, PasswordEncoder encoder) {
        this.email = Textos.normalizarEmail(propriedades.admin().email());
        this.nome = propriedades.admin().nome();
        String senha = propriedades.admin().password();
        if (senha == null || senha.isBlank()) {
            senha = gerarSenha();
            LOG.warn("Senha temporária do administrador ({}) nesta execução: {}", email, senha);
        }
        this.senhaHash = encoder.encode(senha);
    }

    public boolean ehAdmin(String emailNormalizado) {
        return email.equals(emailNormalizado);
    }

    public String email() {
        return email;
    }

    public String nome() {
        return nome;
    }

    public String senhaHash() {
        return senhaHash;
    }

    private static String gerarSenha() {
        SecureRandom aleatorio = new SecureRandom();
        StringBuilder senha = new StringBuilder(TAMANHO_SENHA_GERADA);
        for (int i = 0; i < TAMANHO_SENHA_GERADA; i++) {
            senha.append(ALFABETO.charAt(aleatorio.nextInt(ALFABETO.length())));
        }
        return senha.toString();
    }
}
