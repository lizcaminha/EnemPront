package br.com.info3c.enemPront.model;

import jakarta.persistence.*;

@Entity

public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID_Topico;
    @Column("Matéria")
    private String Materia;
    private String Nome;
    private String Incidencia_Enem;

    public Long getID_Topico(){
        return ID_Topico;
    }
    public void setID_Topico(Long ID_Topico){
        this.ID_Topico = ID_Topico;
    }
    public String getMateria(){
        return Materia;
    }
    public void setMateria(String Materia){
        this.Materia = Materia;
    }
    public String getNome(){
        return Nome;
    }
    public void setNome(String Nome){
        this.Nome = Nome
    }
    public String Incidencia_Enem(){
        return Incidencia_Enem;
    }
    public void setIncidencia_Enem(String Incidencia_Enem){
        this.Incidencia_Enem = Incidencia_Enem;
    }
}
