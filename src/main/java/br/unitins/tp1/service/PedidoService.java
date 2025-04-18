package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.PedidoRequestDTO;
import br.unitins.tp1.dto.PedidoResponseDTO;
import br.unitins.tp1.entity.Pedido;
import br.unitins.tp1.repository.PedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PedidoService {

    @Inject
    PedidoRepository pedidoRepository;

    @Transactional
    public PedidoResponseDTO criarPedido(PedidoRequestDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setCliente(dto.getCliente());
        pedido.setDataPedido(dto.getDataPedido());
        pedido.setItens(dto.getItens());
        pedido.setValorTotal(dto.getValorTotal());
        pedido.setEnderecoEntrega(dto.getEnderecoEntrega());
        pedido.setStatus(dto.getStatus());


        if (dto.getItens() != null) {
            dto.getItens().forEach(item -> item.setPedido(pedido));
        }

        pedidoRepository.persist(pedido);
        return toResponseDTO(pedido);
    }

    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.listAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public PedidoResponseDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado.");
        }
        return toResponseDTO(pedido);
    }

    @Transactional
    public PedidoResponseDTO atualizar(Long id, PedidoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado.");
        }

        pedido.setCliente(dto.getCliente());
        pedido.setDataPedido(dto.getDataPedido());
        pedido.setItens(dto.getItens());
        pedido.setValorTotal(dto.getValorTotal());
        pedido.setEnderecoEntrega(dto.getEnderecoEntrega());
        pedido.setStatus(dto.getStatus());

        if (dto.getItens() != null) {
            dto.getItens().forEach(item -> item.setPedido(pedido));
        }

        return toResponseDTO(pedido);
    }

    @Transactional
    public void deletar(Long id) {
        if (!pedidoRepository.deleteById(id)) {
            throw new NotFoundException("Pedido não encontrado para deletar.");
        }
    }

    private PedidoResponseDTO toResponseDTO(Pedido pedido) {
        return new PedidoResponseDTO(
            pedido.getId(),
            pedido.getCliente(),
            pedido.getDataPedido(),
            pedido.getItens(),
            pedido.getValorTotal(),
            pedido.getEnderecoEntrega(),
            pedido.getStatus()
        );
    }
}
