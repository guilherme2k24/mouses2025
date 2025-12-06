package br.unitins.tp1.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.unitins.tp1.enums.FormaPagamento;
import br.unitins.tp1.enums.StatusDoPedido;

public class PedidoResponseDTO {

    private long id;
    private Long clienteId;
    private String clienteNome;
    private LocalDateTime dataPedido;
    private List<ItemDoPedidoResponseDTO> itens;
    private double valorTotal;
    private StatusDoPedido status;
    private FormaPagamento formaPagamento;

    public PedidoResponseDTO() {
    }

    public PedidoResponseDTO(
            long id,
            Long clienteId,
            String clienteNome,
            LocalDateTime dataPedido,
            List<ItemDoPedidoResponseDTO> itens,
            double valorTotal,
            StatusDoPedido status,
            FormaPagamento formaPagamento) {

        this.id = id;
        this.clienteId = clienteId;
        this.clienteNome = clienteNome;
        this.dataPedido = dataPedido;
        this.itens = itens;
        this.valorTotal = valorTotal;
        this.status = status;
        this.formaPagamento = formaPagamento;

    }

    public long getId() {
        return id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public List<ItemDoPedidoResponseDTO> getItens() {
        return itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public StatusDoPedido getStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public void setItens(List<ItemDoPedidoResponseDTO> itens) {
        this.itens = itens;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setStatus(StatusDoPedido status) {
        this.status = status;
    }

        public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}


