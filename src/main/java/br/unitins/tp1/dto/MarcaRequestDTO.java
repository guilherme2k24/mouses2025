package br.unitins.tp1.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

public class MarcaRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Schema(example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890.png")
    private String fileName;

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
    
    // ✅ ALTERADO
    public String getFileName() { 
        return fileName; 
    }
    
    public void setFileName(String fileName) { 
        this.fileName = fileName; 
    }
}