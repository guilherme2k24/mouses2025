package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class CorRequestDTO {

    @Schema(example = "Preto")
    @NotBlank(message = "O nome da cor é obrigatório")
    private String nome;

    public CorRequestDTO() {
    }

    public CorRequestDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
