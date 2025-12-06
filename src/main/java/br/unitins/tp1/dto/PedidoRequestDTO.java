package br.unitins.tp1.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.unitins.tp1.enums.FormaPagamento;
import br.unitins.tp1.enums.StatusDoPedido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class PedidoRequestDTO {

    @NotNull(message = "O ID do cliente é obrigatório.")
    private Long clienteId;

    private LocalDateTime dataPedido;

    @NotNull(message = "Os itens do pedido são obrigatórios.")
    @NotEmpty(message = "O pedido deve conter pelo menos um item.")
    @Valid
    private List<ItemDoPedidoRequestDTO> itens;

    @NotNull(message = "O endereço do pedido é obrigatório.")
    @Valid
    private EnderecoRequestDTO endereco;

    private StatusDoPedido status;

    @NotNull(message = "A forma de pagamento é obrigatória.")
    private FormaPagamento formaPagamento; 

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
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

    public EnderecoRequestDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoRequestDTO endereco) {
        this.endereco = endereco;
    }

    public StatusDoPedido getStatus() {
        return status;
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
