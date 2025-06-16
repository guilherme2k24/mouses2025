package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ItemDoPedidoRequestDTO {
    
    @NotNull(message = "O ID do pedido é obrigatório.")
    private Long idPedido;

    @NotNull(message = "O ID do mouse é obrigatório.")
    private Long idMouse;

    @NotNull(message = "A quantidade é obrigatória.")
    @Positive(message = "A quantidade deve ser maior que zero.")
    private Integer quantidade;

    @NotNull(message = "O preço unitário é obrigatório.")
    @Positive(message = "O preço unitário deve ser maior que zero.")
    private Double precoUnitario;

    @NotNull(message = "O preço total é obrigatório.")
    @Positive(message = "O preço total deve ser maior que zero.")
    private Double precoTotal;

    public ItemDoPedidoRequestDTO() {
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdMouse() {
        return idMouse;
    }

    public void setIdMouse(Long idMouse) {
        this.idMouse = idMouse;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(Double precoTotal) {
        this.precoTotal = precoTotal;
    }
}
