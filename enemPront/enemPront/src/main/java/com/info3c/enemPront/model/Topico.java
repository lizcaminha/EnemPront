package br.com.info3c.enemPront.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Topico")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Topico")
    private Integer idTopico;

    @ManyToOne
    @JoinColumn(name = "ID_Area_Conhecimento", nullable = false)
    private AreaConhecimento areaConhecimento;

    @Column(name = "Materia")
    private String materia;

    @Column(name = "Nome")
    private String nome;

    @Column(name = "Incidencia_Enem")
    private String incidenciaEnem;


    public Integer getIdTopico() {
        return idTopico;
    }

    public void setIdTopico(Integer idTopico) {
        this.idTopico = idTopico;
    }

    public AreaConhecimento getAreaConhecimento() {
        return areaConhecimento;
    }

    public void setAreaConhecimento(AreaConhecimento areaConhecimento) {
        this.areaConhecimento = areaConhecimento;
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
