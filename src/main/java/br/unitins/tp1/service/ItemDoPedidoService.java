package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.ItemDoPedidoRequestDTO;
import br.unitins.tp1.dto.ItemDoPedidoResponseDTO;
import br.unitins.tp1.entity.ItemDoPedido;
import br.unitins.tp1.repository.ItemDoPedidoRepository;
import br.unitins.tp1.repository.MousesRepository;
import br.unitins.tp1.repository.PedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ItemDoPedidoService {

    @Inject
    ItemDoPedidoRepository repository;

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    MousesRepository mousesRepository;

    @Transactional
    public void salvar(ItemDoPedidoRequestDTO dto) {
        ItemDoPedido item = new ItemDoPedido();
        item.setPedido(pedidoRepository.findById(dto.getIdPedido()));
        item.setMouse(mousesRepository.findById(dto.getIdMouse()));
        item.setQuantidade(dto.getQuantidade());
        item.setPrecoUnitario(dto.getPrecoUnitario());
        item.setPrecoTotal(dto.getPrecoTotal());

        repository.persist(item);
    }

    public List<ItemDoPedidoResponseDTO> listarTodos() {
        return repository.listAll().stream()
            .map(i -> new ItemDoPedidoResponseDTO(
                i.getId(),
                i.getPedido(),
                i.getMouse(),
                i.getQuantidade(),
                i.getPrecoUnitario(),
                i.getPrecoTotal()
            ))
            .collect(Collectors.toList());
    }

    public ItemDoPedidoResponseDTO buscarPorId(Long id) {
        ItemDoPedido item = repository.findById(id);
        if (item == null) {
            throw new NotFoundException("Item não encontrado.");
        }
        return new ItemDoPedidoResponseDTO(
            item.getId(),
            item.getPedido(),
            item.getMouse(),
            item.getQuantidade(),
            item.getPrecoUnitario(),
            item.getPrecoTotal()
        );
    }

    @Transactional
    public void atualizar(Long id, ItemDoPedidoRequestDTO dto) {
        ItemDoPedido item = repository.findById(id);
        if (item == null)
            throw new NotFoundException("Item não encontrado para atualização.");

        item.setPedido(pedidoRepository.findById(dto.getIdPedido()));
        item.setMouse(mousesRepository.findById(dto.getIdMouse()));
        item.setQuantidade(dto.getQuantidade());
        item.setPrecoUnitario(dto.getPrecoUnitario());
        item.setPrecoTotal(dto.getPrecoTotal());
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Item não encontrado para exclusão.");
        }
    }
}
