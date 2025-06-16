package br.unitins.tp1.dto;

public class CategoriaResponseDTO {
    
    private Long id;
    private String nome;

    public CategoriaResponseDTO() {
    }

    public CategoriaResponseDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
