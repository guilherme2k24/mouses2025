package br.unitins.tp1.repository;

import java.util.List;

import br.unitins.tp1.model.Pedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {

    public List<Pedido> findByClienteId(Long clienteId) {
        return list("cliente.id", clienteId);
    }
}
