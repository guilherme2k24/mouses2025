package br.unitins.tp1.repository;

import java.util.List;

import br.unitins.tp1.enums.StatusDoPedido;
import br.unitins.tp1.model.Pedido;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {

    public List<Pedido> findByClienteId(Long clienteId) {
        return list("cliente.id", Sort.descending("dataPedido"), clienteId);
    }

    public List<Pedido> findByStatus(StatusDoPedido status) {
        return list("status", Sort.descending("dataPedido"), status);
    }

    public List<Pedido> findByClienteIdAndStatus(Long clienteId, StatusDoPedido status) {
        return find("cliente.id = ?1 and status = ?2", 
                   Sort.descending("dataPedido"), 
                   clienteId, status).list();
    }

    public List<Pedido> listarTodos() {
        return findAll(Sort.descending("dataPedido")).list();
    }

    public PanacheQuery<Pedido> findAllWithPagination(int pageIndex, int pageSize) {
        return findAll(Sort.descending("dataPedido"))
                .page(pageIndex, pageSize);
    }

    public PanacheQuery<Pedido> findByClienteIdWithPagination(Long clienteId, int pageIndex, int pageSize) {
        return find("cliente.id", Sort.descending("dataPedido"), clienteId)
                .page(pageIndex, pageSize);
    }
}