package br.unitins.tp1.repository;

import br.unitins.tp1.model.Pagamento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PagamentoRepository implements PanacheRepository<Pagamento> {

    public Pagamento findByPedidoId(Long pedidoId) {
        return find("pedido.id", pedidoId).firstResult();
    }

}
