package br.unitins.tp1.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

public class MarcaRequestDTO {


    @NotBlank(message = "O nome é obrigatório")
    private String nome;

        @Schema(example = "https://meusite.com/imagens/g502.png")
    private String imageUrl;

    public MarcaRequestDTO() {
    }

    public MarcaRequestDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
