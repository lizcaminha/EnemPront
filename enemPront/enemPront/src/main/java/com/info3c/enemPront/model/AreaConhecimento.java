package br.com.info3c.enemPront.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Area_Conhecimento")
public class AreaConhecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Area_Conhecimento", nullable = false)
    private Long IdAreaConhecimento;
    @Column(name = "Nome", nullable = false)
    private String Nome;

    public Long getIdAreaConhecimento(){
        return IdAreaConhecimento;
    }
    public void setIdAreaConhecimento(Long IdAreaConhecimento){
        this.IdAreaConhecimento = IdAreaConhecimento;
    }
    public String getNome(){
        return Nome;
    }
    public void setNome(String Nome){
        this.Nome = Nome;
    }
}
