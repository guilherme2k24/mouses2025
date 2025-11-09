package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO para criação/atualização de itens de pedido.
 * Usado apenas dentro do contexto de PedidoRequestDTO.
 * Não deve ser usado diretamente em endpoints separados.
 */
public class ItemDoPedidoRequestDTO {
    
    // The pedido relationship is established automatically in the service layer

    @NotNull(message = "O ID do mouse é obrigatório.")
    private Long idMouse;

    @NotNull(message = "A quantidade é obrigatória.")
    @Positive(message = "A quantidade deve ser maior que zero.")
    private Integer quantidade;

    public ItemDoPedidoRequestDTO() {
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
    
}
