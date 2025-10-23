package br.unitins.tp1.model;
import java.time.LocalDateTime;

import br.unitins.tp1.enums.FormaPagamento;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    private Double valor;
    private LocalDateTime data;

    @OneToOne
    @JoinColumn(name = "pedido_id", unique = true)
    private Pedido pedido;

    public Long getId() { 
        return id; 
    }

    public FormaPagamento getFormaPagamento() { 
        return formaPagamento; 
    }
    public void setFormaPagamento(FormaPagamento formaPagamento) { 
        this.formaPagamento = formaPagamento; 
    }

    public Double getValor() { 
        return valor; 
    }
    public void setValor(Double valor) { 
        this.valor = valor; 
    }

    public LocalDateTime getData() { 
        return data; 
    }
    public void setData(LocalDateTime data) { 
        this.data = data; 
    }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { 
        this.pedido = pedido; 
    }
}
