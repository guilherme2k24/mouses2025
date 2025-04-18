package br.unitins.tp1.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.unitins.tp1.entity.Cliente;
import br.unitins.tp1.entity.ItemDoPedido;
import br.unitins.tp1.enums.StatusDoPedido;

public class PedidoRequestDTO {
    private Cliente cliente;
    private LocalDateTime dataPedido;
    private List<ItemDoPedido> itens;
    private double valorTotal;
    private String enderecoEntrega;
    private StatusDoPedido status;
    
    public PedidoRequestDTO() {
    }

    public PedidoRequestDTO(Cliente cliente, LocalDateTime dataPedido, List<ItemDoPedido> itens, double valorTotal, String enderecoEntrega,
            StatusDoPedido status) {
        this.cliente = cliente;
        this.dataPedido = dataPedido;
        this.itens = itens;
        this.valorTotal = valorTotal;
        this.enderecoEntrega = enderecoEntrega;
        this.status = status;
    }

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

    public List<ItemDoPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemDoPedido> itens) {
        this.itens = itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public StatusDoPedido getStatus() {
        return status;
    }

    public void setStatus(StatusDoPedido status) {
        this.status = status;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }
}