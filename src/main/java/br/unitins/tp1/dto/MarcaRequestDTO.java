package br.unitins.tp1.dto;

public class MarcaRequestDTO {

    private String nome;

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
}
