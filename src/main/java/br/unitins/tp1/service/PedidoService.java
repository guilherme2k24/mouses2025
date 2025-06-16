package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.ItemDoPedidoRequestDTO;
import br.unitins.tp1.dto.PedidoRequestDTO;
import br.unitins.tp1.dto.PedidoResponseDTO;
import br.unitins.tp1.model.Cliente;
import br.unitins.tp1.model.Estoque;
import br.unitins.tp1.model.ItemDoPedido;
import br.unitins.tp1.model.Mouses;
import br.unitins.tp1.model.Pedido;
import br.unitins.tp1.repository.EstoqueRepository;
import br.unitins.tp1.repository.MousesRepository;
import br.unitins.tp1.repository.PedidoRepository;
import br.unitins.tp1.repository.UsuarioRepository;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;

@ApplicationScoped
public class PedidoService {

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    MousesRepository mouseRepository;

    @Inject
    EstoqueRepository estoqueRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    SecurityIdentity securityIdentity;

    @Transactional
    public PedidoResponseDTO criar(PedidoRequestDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setCliente(dto.getCliente());
        pedido.setDataPedido(dto.getDataPedido());
        pedido.setStatus(dto.getStatus());
        pedido.setEnderecoEntrega(dto.getEnderecoEntrega());

        List<ItemDoPedido> itens = dto.getItens() != null
                ? dto.getItens().stream()
                        .map(itemDto -> toItemDoPedido(itemDto, pedido))
                        .collect(Collectors.toList())
                : List.of();

        pedido.setItens(itens);
        pedido.setValorTotal(calcularValorTotal(itens));

        pedidoRepository.persist(pedido);
        return toResponseDTO(pedido);
    }

    public List<PedidoResponseDTO> listarTodos() {
        if (securityIdentity.hasRole("Adm")) {
            return pedidoRepository.listAll().stream()
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
        } else {
            return listarPorClienteLogado();
        }
    }

    public PedidoResponseDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado.");
        }
        return toResponseDTO(pedido);
    }

    @Transactional
    public void atualizar(Long id, PedidoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado.");
        }

        pedido.setCliente(dto.getCliente());
        pedido.setDataPedido(dto.getDataPedido());
        pedido.setStatus(dto.getStatus());
        pedido.setEnderecoEntrega(dto.getEnderecoEntrega());

        pedido.getItens().clear();
        List<ItemDoPedido> novosItens = dto.getItens() != null
                ? dto.getItens().stream()
                        .map(itemDto -> toItemDoPedido(itemDto, pedido))
                        .collect(Collectors.toList())
                : List.of();

        pedido.getItens().addAll(novosItens);
        pedido.setValorTotal(calcularValorTotal(novosItens));
    }

    @Transactional
    public void deletar(Long id) {
        if (!pedidoRepository.deleteById(id)) {
            throw new NotFoundException("Pedido não encontrado para deletar.");
        }
    }

    public List<PedidoResponseDTO> listarPorClienteLogado() {
        Long clienteId = getClienteIdLogado();
        return pedidoRepository.findByClienteId(clienteId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private Long getClienteIdLogado() {
        String username = securityIdentity.getPrincipal().getName();
        return usuarioRepository.getClienteByUsername(username)
                .map(Cliente::getId)
                .orElseThrow(() -> new NotFoundException("Cliente com username '" + username + "' não encontrado"));
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

    private ItemDoPedido toItemDoPedido(ItemDoPedidoRequestDTO dto, Pedido pedido) {
        Mouses mouses = mouseRepository.findById(dto.getIdMouse());
        if (mouses == null) {
            throw new NotFoundException("Mouse não encontrado com ID: " + dto.getIdMouse());
        }

        Estoque estoque = estoqueRepository.findByMouseId(mouses.getId())
                .orElseThrow(() -> new NotFoundException("Estoque não encontrado para o mouse ID: " + mouses.getId()));

        if (dto.getQuantidade() > estoque.getQuantidade()) {
            throw new WebApplicationException("Estoque insuficiente para o mouse: " + mouses.getModelo(), 400);
        }

        estoque.setQuantidade(estoque.getQuantidade() - dto.getQuantidade());
        estoqueRepository.getEntityManager().flush();

        ItemDoPedido item = new ItemDoPedido();
        item.setPedido(pedido);
        item.setMouse(mouses);
        item.setQuantidade(dto.getQuantidade());
        item.setPrecoUnitario(dto.getPrecoUnitario());
        item.setPrecoTotal(dto.getPrecoTotal());

        return item;
    }

    private Double calcularValorTotal(List<ItemDoPedido> itens) {
        return itens.stream()
                .mapToDouble(ItemDoPedido::getPrecoTotal)
                .sum();
    }
}
