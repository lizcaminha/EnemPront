package br.com.info3c.enemPront.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
public class Usuario {

    @id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long ID_Usuario;
    private String Nome;
    private String Email;
    private String Senha;
    private LocalDate Dt_nascimento;
    private String Status;

    public Long getID_Usuario() {
        return ID_Usuario;
    }

    public void setID_Usuario(Long ID_Usuario) {
        this.ID_Usuario = ID_Usuario;
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
    public LocalDate getDt_nascimento() {
        return Dt_nascimento;
    }
    public void setDt_nascimento(LocalDate Dt_nascimento) {
        this.Dt_nascimento = Dt_nascimento;
    }
    public String getStatus() {
        return Status;
    }
    public void setStatus(String Status) {
        this.Status = Status;
    }
}