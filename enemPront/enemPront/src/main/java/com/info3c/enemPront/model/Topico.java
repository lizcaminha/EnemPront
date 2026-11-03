package com.info3c.enemPront.model;

import jakarta.persistence.*;

@Entity

public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Topico")
    private Long IdTopico;

    @ManyToOne
    @JoinColumn(name = "ID_Area", nullable = false)
    private AreaConhecimento IdArea;

    @Column(name = "Matéria")
    private String Materia;

    @Column(name = "Nome")
    private String Nome;

    @Column(name = "Incidencia_Enem")
    private String IncidenciaEnem;

    public AreaConhecimento getIdArea() {
        return IdArea;
    }
    public void setIdArea(AreaConhecimento IdArea) {
        this.IdArea = IdArea;
    }
}
