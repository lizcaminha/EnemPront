package br.com.info3c.enemPront.model;

import jakarta.persistence.*;

@Entity
public class Area_Conhecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID_Area_Conhecimento;
    private String Nome;

    public Long getID_Area_Conhecimento(){
        return ID_Area_Conhecimento;
    }
    public void setID_Area_Conhecimento(Long ID_Area_Conhecimento){
        this.ID_Area_Conhecimento = ID_Area_Conhecimento;
    }
    public String getNome(){
        return Nome;
    }
    public void setNome(String Nome){
        this.Nome = Nome;
    }
}
