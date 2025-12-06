package br.unitins.tp1.dto;

public class MarcaResponseDTO {

    private Long id;
    private String nome;
    private String imageUrl;

    public MarcaResponseDTO() {
    }

    public MarcaResponseDTO(Long id, String nome, String imageUrl) {
        this.id = id;
        this.nome = nome;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
