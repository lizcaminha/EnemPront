package br.com.info3c.enemPront.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Area_Conhecimento")
public class AreaConhecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Area_Conhecimento")
    private Integer idAreaConhecimento;

    @Column(name = "Nome")
    private String nome;

    public Integer getIdAreaConhecimento() {
        return idAreaConhecimento;
    }

    public void setIdAreaConhecimento(Integer idAreaConhecimento) {
        this.idAreaConhecimento = idAreaConhecimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
