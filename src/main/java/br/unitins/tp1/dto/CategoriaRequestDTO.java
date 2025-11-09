package br.unitins.tp1.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

public class CategoriaRequestDTO {

    @Schema(example = "Gamer")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    public CategoriaRequestDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
