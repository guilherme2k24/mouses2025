package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class EstoqueRequestDTO {
    @NotNull(message = "A quantidade é obrigatória.")
    @PositiveOrZero(message = "A quantidade não pode ser negativa.")
    private Integer quantidade;

    @NotBlank(message = "A localização é obrigatória.")
    @Size(min = 2, message = "A localização deve conter pelo menos 2 caracteres.")
    private String localizacao;

    @NotNull(message = "O ID do mouse é obrigatório.")
    private Long mouseId;

    public EstoqueRequestDTO() {
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

    public Long getMouseId() {
        return mouseId;
    }

    public void setMouseId(Long mouseId) {
        this.mouseId = mouseId;
    }
}