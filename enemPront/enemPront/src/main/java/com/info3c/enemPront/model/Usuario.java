package br.com.info3c.enemPront.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "Usuario")
public class Usuario {

    @id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Usuario")
    private Long IdUsuario;
    @Column(name = "Nome", nullable = false)
    private String Nome;
    @Column(name = "Email", nullable = false)
    private String Email;
    @Column(name = "Senha", nullable = false)
    private String Senha;
    @Column(name = "Dt_nascimento", nullable = false)
    private LocalDate DtNascimento;
    @Column(name = "Status", nullable = false)
    private String Status;

    public Long getIdUsuario() {
        return IdUsuario;
    }
    public void setIdUsuario(Long IdUsuario) {
        this.IdUsuario = IdUsuario;
    }
    public String getNome() {
        return Nome;
    }
    public void setNome(String Nome){
        this.Nome = Nome;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String Email){
        this.Email = Email;
    }
    public String getSenha() {
        return Senha;
    }
    public void setSenha(String Senha){
        this.Senha = Senha;
    }
    public LocalDate getDtNascimento() {
        return DtNascimento;
    }
    public void setDtNascimento(LocalDate DtNascimento) {
        this.DtNascimento = DtNascimento;
    }
    public String getStatus() {
        return Status;
    }
    public void setStatus(String Status) {
        this.Status = Status;
    }
}