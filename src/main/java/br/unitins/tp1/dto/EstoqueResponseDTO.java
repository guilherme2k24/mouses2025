package br.unitins.tp1.dto;

public class EstoqueResponseDTO {
    private Long id;
    private Integer quantidade;
    private String localizacao;
    private MouseResponseDTO mouse;

    public EstoqueResponseDTO() {
    }
    
    public EstoqueResponseDTO(Long id, Integer quantidade, String localizacao, MouseResponseDTO mouse) {
        this.id = id;
        this.quantidade = quantidade;
        this.localizacao = localizacao;
        this.mouse = mouse;
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

    public MouseResponseDTO getMouse() {
        return mouse;
    }

    public void setMouse(MouseResponseDTO mouse) {
        this.mouse = mouse;
    }
}