package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.ItemDoPedidoRequestDTO;
import br.unitins.tp1.dto.ItemDoPedidoResponseDTO;
import br.unitins.tp1.model.ItemDoPedido;
import br.unitins.tp1.repository.ItemDoPedidoRepository;
import br.unitins.tp1.repository.MouseRepository;
import br.unitins.tp1.repository.PedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ItemDoPedidoService {
    
    @Inject
    ItemDoPedidoRepository itemRepository;

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    MouseRepository mousesRepository;

    @Transactional
    public ItemDoPedidoResponseDTO criar(ItemDoPedidoRequestDTO dto) {
        var pedido = pedidoRepository.findById(dto.getIdPedido());
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado.");
        }

        var mouse = mousesRepository.findById(dto.getIdMouse());
        if (mouse == null) {
            throw new NotFoundException("Mouse não encontrado.");
        }

        ItemDoPedido item = new ItemDoPedido();
        item.setPedido(pedido);
        item.setMouse(mouse);
        item.setQuantidade(dto.getQuantidade());
        item.setPrecoUnitario(dto.getPrecoUnitario());
        item.setPrecoTotal(dto.getPrecoTotal());

        itemRepository.persist(item);
        return toResponseDTO(item);
    }

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

    @Transactional
    public void atualizar(Long id, ItemDoPedidoRequestDTO dto) {
        ItemDoPedido item = itemRepository.findById(id);
        if (item == null) {
            throw new NotFoundException("Item do pedido não encontrado.");
        }

        var pedido = pedidoRepository.findById(dto.getIdPedido());
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado.");
        }

        var mouse = mousesRepository.findById(dto.getIdMouse());
        if (mouse == null) {
            throw new NotFoundException("Mouse não encontrado.");
        }

        item.setPedido(pedido);
        item.setMouse(mouse);
        item.setQuantidade(dto.getQuantidade());
        item.setPrecoUnitario(dto.getPrecoUnitario());
        item.setPrecoTotal(dto.getPrecoTotal());
    }

    @Transactional
    public void deletar(Long id) {
        if (!itemRepository.deleteById(id)) {
            throw new NotFoundException("Item do pedido não encontrado para exclusão.");
        }
    }

    private ItemDoPedidoResponseDTO toResponseDTO(ItemDoPedido item) {
        return new ItemDoPedidoResponseDTO(
            item.getId(),
            item.getPedido().getId(),
            item.getMouse().getId(),
            item.getQuantidade(),
            item.getPrecoUnitario(),
            item.getPrecoTotal()
        );
    }
}
