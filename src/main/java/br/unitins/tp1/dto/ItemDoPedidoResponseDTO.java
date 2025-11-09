package br.unitins.tp1.dto;

public class ItemDoPedidoResponseDTO {

    private Long id;
    private Long idPedido;   
    private Long idMouse;    
    private String mouseModelo;
    private Integer quantidade;
    private Double precoUnitario;  
    private Double precoTotal;

    public ItemDoPedidoResponseDTO() {}

    public ItemDoPedidoResponseDTO(Long id, Long idPedido, Long idMouse, String mouseModelo, 
                                   Integer quantidade, Double precoUnitario, Double precoTotal) {
        this.id = id;
        this.idPedido = idPedido;
        this.idMouse = idMouse;
        this.mouseModelo = mouseModelo;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.precoTotal = precoTotal;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getIdPedido() { return idPedido; }
    public void setIdPedido(Long idPedido) { this.idPedido = idPedido; }
    
    public Long getIdMouse() { return idMouse; }
    public void setIdMouse(Long idMouse) { this.idMouse = idMouse; }
    
    public String getMouseModelo() { return mouseModelo; }
    public void setMouseModelo(String mouseModelo) { this.mouseModelo = mouseModelo; }
    
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    
    public Double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(Double precoUnitario) { this.precoUnitario = precoUnitario; }
    
    public Double getPrecoTotal() { return precoTotal; }
    public void setPrecoTotal(Double precoTotal) { this.precoTotal = precoTotal; }
}
