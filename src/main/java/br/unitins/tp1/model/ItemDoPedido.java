package br.unitins.tp1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class ItemDoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;

    @Column(nullable = false)
    private Double precoUnitario;

    private Double precoTotal;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "mouse_id")
    private Mouse mouse;

    public ItemDoPedido() {}

    public ItemDoPedido(Pedido pedido, Mouse mouse, Integer quantidade, Double precoUnitario) {
        this.pedido = pedido;
        this.mouse = mouse;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        calcularPrecoTotal();
    }

    @PrePersist
    @PreUpdate
    private void calcularPrecoTotal() {
        if (this.precoUnitario != null && this.quantidade != null) {
            this.precoTotal = this.precoUnitario * this.quantidade;
        }
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        calcularPrecoTotal();
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
        calcularPrecoTotal();
    }

    public Double getPrecoTotal() {
        return precoTotal;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }
}
