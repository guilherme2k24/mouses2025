package br.unitins.tp1.dto;


import br.unitins.tp1.model.Mouse;

public class EstoqueResponseDTO {
    private Long id;
    private Integer quantidade;
    private String localizacao;
    private Mouse mouses;

    public EstoqueResponseDTO() {
    }
    
    public EstoqueResponseDTO(Long id, Integer quantidade, String localizacao, Mouse mouses) {
        this.id = id;
        this.quantidade = quantidade;
        this.localizacao = localizacao;
        this.mouses = mouses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Mouse getMouses() {
        return mouses;
    }

    public void setMouses(Mouse mouses) {
        this.mouses = mouses;
    }
}
