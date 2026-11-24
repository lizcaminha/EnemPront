package com.info3c.enemPront.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Topico")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Topico")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_Area", nullable = false)
    private AreaConhecimento area;

    @Column(name = "Materia", nullable = false, length = 25)
    private String materia;

    @Column(name = "Nome", nullable = false, length = 30)
    private String nome;

    @Column(name = "Incidencia_Enem", nullable = false, length = 1)
    private String incidenciaEnem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AreaConhecimento getArea() {
        return area;
    }

    public void setArea(AreaConhecimento area) {
        this.area = area;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIncidenciaEnem() {
        return incidenciaEnem;
    }

    public void setIncidenciaEnem(String incidenciaEnem) {
        this.incidenciaEnem = incidenciaEnem;
    }
}
