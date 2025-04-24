package br.unitins.tp1.dto;

import java.time.LocalDateTime;

import br.unitins.tp1.enums.FormaPagamento;

public class PagamentoRequestDTO {
    private FormaPagamento formaPagamento;
    private double valor;
    private LocalDateTime data;
    private Long pedidoId;

    public PagamentoRequestDTO() {
    }

    public PagamentoRequestDTO(FormaPagamento formaPagamento, double valor, LocalDateTime data, Long pedidoId) {
        this.formaPagamento = formaPagamento;
        this.valor = valor;
        this.data = data;
        this.pedidoId = pedidoId;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }
}
