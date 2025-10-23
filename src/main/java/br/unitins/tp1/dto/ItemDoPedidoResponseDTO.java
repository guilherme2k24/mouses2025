package br.unitins.tp1.dto;

public class ItemDoPedidoResponseDTO {

    private Long id;
    private Long idPedido;   // Apenas o ID do Pedido
    private Long idMouse;    // Apenas o ID do Mouse
    private Integer quantidade;
    private Double precoUnitario;
    private Double precoTotal;

    // Construtor vazio
    public ItemDoPedidoResponseDTO() {
    }

    // Construtor completo
    public ItemDoPedidoResponseDTO(Long id, Long idPedido, Long idMouse, Integer quantidade, Double precoUnitario, Double precoTotal) {
        this.id = id;
        this.idPedido = idPedido;
        this.idMouse = idMouse;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.precoTotal = precoTotal;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public Long getIdMouse() {
        return idMouse;
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

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public void setIdMouse(Long idMouse) {
        this.idMouse = idMouse;
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
