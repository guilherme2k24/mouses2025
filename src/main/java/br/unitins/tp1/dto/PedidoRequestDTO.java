package br.unitins.tp1.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.unitins.tp1.enums.StatusDoPedido;
import br.unitins.tp1.model.Cliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PedidoRequestDTO {

    @NotNull(message = "O cliente é obrigatório.")
    private Cliente cliente;

    @NotNull(message = "A data do pedido é obrigatória.")
    private LocalDateTime dataPedido;

    private List<@NotNull ItemDoPedidoRequestDTO> itens;

    private double valorTotal;

    @NotBlank(message = "O endereço de entrega é obrigatório.")
    @Size(min = 5, message = "O endereço deve ter pelo menos 5 caracteres.")
    private String enderecoEntrega;

    @NotNull(message = "O status do pedido é obrigatório.")
    private StatusDoPedido status;

    // Getters e Setters
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public List<ItemDoPedidoRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemDoPedidoRequestDTO> itens) {
        this.itens = itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public StatusDoPedido getStatus() {
        return status;
    }

    public void setStatus(StatusDoPedido status) {
        this.status = status;
    }
}
