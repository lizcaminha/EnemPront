package com.info3c.enemPront.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class QuestaoForm {

    public static final int QUANTIDADE_TOPICOS = 3;

    @NotNull(message = "Selecione a edição do ENEM")
    private Long edicaoId;

    @NotBlank(message = "Informe o enunciado")
    @Size(max = 5000, message = "O enunciado deve ter no máximo 5000 caracteres")
    private String enunciado;

    @NotBlank(message = "Informe a alternativa A")
    @Size(max = 200, message = "Máximo de 200 caracteres")
    private String alternativaA;

    @NotBlank(message = "Informe a alternativa B")
    @Size(max = 200, message = "Máximo de 200 caracteres")
    private String alternativaB;

    @NotBlank(message = "Informe a alternativa C")
    @Size(max = 200, message = "Máximo de 200 caracteres")
    private String alternativaC;

    @NotBlank(message = "Informe a alternativa D")
    @Size(max = 200, message = "Máximo de 200 caracteres")
    private String alternativaD;

    @NotBlank(message = "Informe a alternativa E")
    @Size(max = 200, message = "Máximo de 200 caracteres")
    private String alternativaE;

    @NotBlank(message = "Selecione o gabarito")
    @Pattern(regexp = "[A-E]", message = "Gabarito inválido")
    private String alternativaCorreta;

    private List<TopicoSlot> topicos = novosSlots();

    public static List<TopicoSlot> novosSlots() {
        List<TopicoSlot> slots = new ArrayList<>();
        for (int i = 0; i < QUANTIDADE_TOPICOS; i++) {
            slots.add(new TopicoSlot());
        }
        return slots;
    }

    public Long getEdicaoId() {
        return edicaoId;
    }

    public void setEdicaoId(Long edicaoId) {
        this.edicaoId = edicaoId;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getAlternativaA() {
        return alternativaA;
    }

    public void setAlternativaA(String alternativaA) {
        this.alternativaA = alternativaA;
    }

    public String getAlternativaB() {
        return alternativaB;
    }

    public void setAlternativaB(String alternativaB) {
        this.alternativaB = alternativaB;
    }

    public String getAlternativaC() {
        return alternativaC;
    }

    public void setAlternativaC(String alternativaC) {
        this.alternativaC = alternativaC;
    }

    public String getAlternativaD() {
        return alternativaD;
    }

    public void setAlternativaD(String alternativaD) {
        this.alternativaD = alternativaD;
    }

    public String getAlternativaE() {
        return alternativaE;
    }

    public void setAlternativaE(String alternativaE) {
        this.alternativaE = alternativaE;
    }

    public String getAlternativaCorreta() {
        return alternativaCorreta;
    }

    public void setAlternativaCorreta(String alternativaCorreta) {
        this.alternativaCorreta = alternativaCorreta;
    }

    public List<TopicoSlot> getTopicos() {
        return topicos;
    }

    public void setTopicos(List<TopicoSlot> topicos) {
        this.topicos = topicos;
    }

    public static class TopicoSlot {

        private Long topicoId;
        private String dificuldade;

        public Long getTopicoId() {
            return topicoId;
        }

        public void setTopicoId(Long topicoId) {
            this.topicoId = topicoId;
        }

        public String getDificuldade() {
            return dificuldade;
        }

        public void setDificuldade(String dificuldade) {
            this.dificuldade = dificuldade;
        }
    }
}
