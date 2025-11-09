package br.unitins.tp1.repository;

import java.util.List;

import br.unitins.tp1.enums.StatusDoPedido;
import br.unitins.tp1.model.Pedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {

    public List<Pedido> findByClienteId(Long clienteId) {
        return list("cliente.id", clienteId);
    }

    public List<Pedido> findByStatus(StatusDoPedido status) {
        return list("status", status);
    }

    public List<Pedido> findByClienteIdAndStatus(Long clienteId, StatusDoPedido status) {
        return list("cliente.id = ?1 and status = ?2", clienteId, status);
    }
}
