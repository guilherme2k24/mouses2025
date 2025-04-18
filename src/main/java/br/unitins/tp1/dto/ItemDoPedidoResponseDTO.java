package br.unitins.tp1.dto;

import br.unitins.tp1.entity.Mouses;
import br.unitins.tp1.entity.Pedido;

public class ItemDoPedidoResponseDTO {
    private Long id;
    private Pedido pedido;
    private Mouses mouse;
    private Integer quantidade;
    private Double precoUnitario;
    private Double precoTotal;

    public ItemDoPedidoResponseDTO() {
    }

    public ItemDoPedidoResponseDTO(Long id, Pedido pedido, Mouses mouse, Integer quantidade, Double precoUnitario, Double precoTotal) {
        this.id = id;
        this.pedido = pedido;
        this.mouse = mouse;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.precoTotal = precoTotal;
    }

    public Long getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Mouses getMouse() {
        return mouse;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public Double getPrecoTotal() {
        return precoTotal;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setMouse(Mouses mouse) {
        this.mouse = mouse;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public void setPrecoTotal(Double precoTotal) {
        this.precoTotal = precoTotal;
    }
}
