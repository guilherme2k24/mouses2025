package br.unitins.tp1.repository;

import java.util.List;

import br.unitins.tp1.model.ItemDoPedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemDoPedidoRepository implements PanacheRepository<ItemDoPedido> {

    public List<ItemDoPedido> findByClienteId(Long clienteId) {
        return find("pedido.cliente.id = ?1", clienteId).list();
    }

    public ItemDoPedido findByIdAndClienteId(Long itemId, Long clienteId) {
        return find("id = ?1 and pedido.cliente.id = ?2", itemId, clienteId).firstResult();
    }
}
