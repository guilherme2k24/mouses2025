package br.unitins.tp1.dto;

import java.time.LocalDateTime;

import br.unitins.tp1.enums.FormaPagamento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PagamentoRequestDTO {
    
    @NotNull(message = "A forma de pagamento é obrigatória.")
    private FormaPagamento formaPagamento;

    @Positive(message = "O valor do pagamento deve ser positivo.")
    private double valor;

    @NotNull(message = "A data do pagamento é obrigatória.")
    private LocalDateTime data;

    @NotNull(message = "O ID do pedido é obrigatório.")
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
