package com.info3c.enemPront.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "Usuario")
public class Usuario {

    public static final String ATIVO = "A";
    public static final String INATIVO = "I";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Usuario")
    private Long id;

    @Column(name = "Email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "Senha", nullable = false, length = 100)
    private String senha;

    @Column(name = "Nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "Dt_nascimento", nullable = false)
    private LocalDate dtNascimento;

    @Column(name = "Status", nullable = false, length = 1)
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isAtivo() {
        return ATIVO.equals(status);
    }
}
