package br.unitins.tp1.model;

import java.time.LocalDateTime;
import java.util.List;

import br.unitins.tp1.enums.FormaPagamento;
import br.unitins.tp1.enums.StatusDoPedido;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private LocalDateTime dataPedido;

    @OneToMany(mappedBy= "pedido", cascade = CascadeType.ALL)
    private List<ItemDoPedido> itens;

    private double valorTotal;  

    @Embedded
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    private StatusDoPedido status;

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;
    
    
    public Pedido() {
    }

    public Pedido(long id, Cliente cliente, LocalDateTime dataPedido, List<ItemDoPedido> itens, 
                  double valorTotal, Endereco endereco, StatusDoPedido status, FormaPagamento formaPagamento) {
        this.id = id;
        this.cliente = cliente;
        this.dataPedido = dataPedido;
        this.itens = itens;
        this.valorTotal = valorTotal;
        this.status = status; 
        this.endereco = endereco;
        this.formaPagamento = formaPagamento;
    }
    public long getId() {
        return id;
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

        public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
        public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}
