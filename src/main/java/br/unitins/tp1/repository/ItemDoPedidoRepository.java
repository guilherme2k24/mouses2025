package br.unitins.tp1.repository;

import br.unitins.tp1.entity.ItemDoPedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemDoPedidoRepository implements PanacheRepository<ItemDoPedido> {
}
