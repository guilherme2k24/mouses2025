package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class SensorRequestDTO {

    @Schema(example = "HERO 25K")
    @NotBlank(message = "O nome do sensor é obrigatório")
    private String nome;

    public SensorRequestDTO() {
    }

    public SensorRequestDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
