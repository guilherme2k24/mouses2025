package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.ItemDoPedidoResponseDTO;
import br.unitins.tp1.model.ItemDoPedido;
import br.unitins.tp1.repository.ItemDoPedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;


@Deprecated
@ApplicationScoped
public class ItemDoPedidoService {
    
    @Inject
    ItemDoPedidoRepository itemRepository;

    public List<ItemDoPedidoResponseDTO> listarTodos() {
        return itemRepository.listAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ItemDoPedidoResponseDTO> listarPorClienteId(Long clienteId) {
        return itemRepository.findByClienteId(clienteId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ItemDoPedidoResponseDTO buscarPorId(Long id) {
        ItemDoPedido item = itemRepository.findById(id);
        if (item == null) {
            throw new NotFoundException("Item do pedido não encontrado.");
        }
        return toResponseDTO(item);
    }

    public ItemDoPedidoResponseDTO buscarPorIdSePertencerAoCliente(Long itemId, Long clienteId) {
        ItemDoPedido item = itemRepository.findByIdAndClienteId(itemId, clienteId);
        if (item == null) {
            return null;
        }
        return toResponseDTO(item);
    }

    private ItemDoPedidoResponseDTO toResponseDTO(ItemDoPedido item) {
        Long mouseId = item.getMouse() != null ? item.getMouse().getId() : null;
        String mouseModelo = item.getMouse() != null ? item.getMouse().getModelo() : "Mouse não encontrado";
        
        return new ItemDoPedidoResponseDTO(
            item.getId(),
            item.getPedido().getId(),
            mouseId,
            mouseModelo,
            item.getQuantidade(),
            item.getPrecoUnitario(),
            item.getPrecoTotal()
        );
    }
}
