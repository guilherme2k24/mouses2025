package br.unitins.tp1.dto;

import java.time.LocalDateTime;

import br.unitins.tp1.enums.FormaPagamento;
import br.unitins.tp1.model.Pagamento;

public class PagamentoResponseDTO {
    private long id;
    private FormaPagamento formaPagamento;
    private double valor;
    private LocalDateTime data;
    private Long pedidoId;

    public PagamentoResponseDTO() {
    }

    public PagamentoResponseDTO(Pagamento pagamento) {
        this.id = pagamento.getId();
        this.formaPagamento = pagamento.getFormaPagamento();
        this.valor = pagamento.getValor();
        this.data = pagamento.getData();
        this.pedidoId = pagamento.getPedido().getId();
    }

    public long getId() {
        return id;
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
