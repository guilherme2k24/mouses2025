package br.unitins.tp1.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.EnderecoRequestDTO;
import br.unitins.tp1.dto.ItemDoPedidoRequestDTO;
import br.unitins.tp1.dto.ItemDoPedidoResponseDTO;
import br.unitins.tp1.dto.PedidoRequestDTO;
import br.unitins.tp1.dto.PedidoResponseDTO;
import br.unitins.tp1.enums.StatusDoPedido;
import br.unitins.tp1.enums.FormaPagamento;
import br.unitins.tp1.model.Cliente;
import br.unitins.tp1.model.Endereco;
import br.unitins.tp1.model.Estoque;
import br.unitins.tp1.model.ItemDoPedido;
import br.unitins.tp1.model.Mouse;
import br.unitins.tp1.model.Pedido;
import br.unitins.tp1.repository.ClienteRepository;
import br.unitins.tp1.repository.EstoqueRepository;
import br.unitins.tp1.repository.MouseRepository;
import br.unitins.tp1.repository.PedidoRepository;
import br.unitins.tp1.repository.UsuarioRepository;
import io.quarkus.security.ForbiddenException;
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
    MouseRepository mouseRepository;

    @Inject
    EstoqueRepository estoqueRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    SecurityIdentity securityIdentity;

    @Transactional
    public PedidoResponseDTO criar(PedidoRequestDTO dto) {
        if (dto.getItens() == null || dto.getItens().isEmpty()) {
            throw new WebApplicationException("O pedido deve conter pelo menos um item.", 400);
        }

        if (dto.getClienteId() == null) {
            throw new WebApplicationException("O ID do cliente é obrigatório.", 400);
        }

        if (dto.getFormaPagamento() == null) {
            throw new WebApplicationException("A forma de pagamento é obrigatória.", 400);
        }

        Cliente cliente = clienteRepository.findById(dto.getClienteId());
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado com ID: " + dto.getClienteId());
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);

        if (dto.getDataPedido() != null)
            pedido.setDataPedido(dto.getDataPedido());
        else
            pedido.setDataPedido(LocalDateTime.now());

        if (dto.getStatus() != null)
            pedido.setStatus(dto.getStatus());
        else
            pedido.setStatus(StatusDoPedido.AGUARDANDO_PAGAMENTO);

        pedido.setFormaPagamento(dto.getFormaPagamento());

        Endereco endereco = toEnderecoEntity(dto.getEndereco());
        pedido.setEndereco(endereco);

        List<ItemDoPedido> itens = new ArrayList<>();
        for (ItemDoPedidoRequestDTO itemDto : dto.getItens()) {
            ItemDoPedido item = processarItemDoPedido(itemDto, pedido);
            itens.add(item);
        }

        pedido.setItens(itens);
        pedido.setValorTotal(calcularValorTotal(itens));

        pedidoRepository.persist(pedido);
        return toResponseDTO(pedido);
    }

    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.listarTodos()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoResponseDTO> listarPorClienteLogado() {
        Long clienteId = getClienteIdLogado();
        return pedidoRepository.findByClienteId(clienteId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoResponseDTO> buscarPorStatusParaAdmin(String statusStr) {
        StatusDoPedido status;
        try {
            status = StatusDoPedido.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new WebApplicationException("Status inválido: " + statusStr, 400);
        }

        return pedidoRepository.findByStatus(status)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoResponseDTO> buscarPorStatusParaUsuarioLogado(String statusStr) {
        StatusDoPedido status;
        try {
            status = StatusDoPedido.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new WebApplicationException("Status inválido: " + statusStr, 400);
        }

        Long clienteId = getClienteIdLogado();
        return pedidoRepository.findByClienteIdAndStatus(clienteId, status)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public PedidoResponseDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado.");
        }
        
        
        if (!securityIdentity.hasRole("ADM")) {
            Long clienteIdLogado = getClienteIdLogado();
            if (!pedido.getCliente().getId().equals(clienteIdLogado)) {
                throw new ForbiddenException("Você não tem permissão para acessar este pedido");
            }
        }
        
        return toResponseDTO(pedido);
    }

    public void verificarPermissaoCancelamento(Long pedidoId) {
        if (!securityIdentity.hasRole("ADM")) {
            Long clienteIdLogado = getClienteIdLogado();
            Pedido pedido = pedidoRepository.findById(pedidoId);
            
            if (pedido == null) {
                throw new NotFoundException("Pedido não encontrado.");
            }
            
            if (!pedido.getCliente().getId().equals(clienteIdLogado)) {
                throw new ForbiddenException("Você não tem permissão para cancelar este pedido");
            }
        }
    }

    @Transactional
    public void atualizar(Long id, PedidoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado.");
        }

        if (dto.getItens() == null || dto.getItens().isEmpty()) {
            throw new WebApplicationException("O pedido deve conter pelo menos um item.", 400);
        }

        if (dto.getClienteId() == null) {
            throw new WebApplicationException("O ID do cliente é obrigatório.", 400);
        }

        if (dto.getFormaPagamento() == null) {
            throw new WebApplicationException("A forma de pagamento é obrigatória.", 400);
        }

        Cliente cliente = clienteRepository.findById(dto.getClienteId());
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado com ID: " + dto.getClienteId());
        }

        pedido.setCliente(cliente);

        if (dto.getDataPedido() != null)
            pedido.setDataPedido(dto.getDataPedido());

        if (dto.getStatus() != null)
            pedido.setStatus(dto.getStatus());

        pedido.setFormaPagamento(dto.getFormaPagamento());

        pedido.setEndereco(toEnderecoEntity(dto.getEndereco()));

        
        for (ItemDoPedido itemAntigo : pedido.getItens()) {
            Estoque estoque = estoqueRepository.findByMouseId(itemAntigo.getMouse().getId())
                    .orElse(null);
            if (estoque != null) {
                estoque.setQuantidade(estoque.getQuantidade() + itemAntigo.getQuantidade());
            }
        }

        pedido.getItens().clear();

        
        List<ItemDoPedido> novosItens = new ArrayList<>();
        for (ItemDoPedidoRequestDTO itemDto : dto.getItens()) {
            ItemDoPedido item = processarItemDoPedido(itemDto, pedido);
            novosItens.add(item);
        }

        pedido.getItens().addAll(novosItens);
        pedido.setValorTotal(calcularValorTotal(novosItens));
    }

    @Transactional
    public void atualizarStatus(Long id, String statusStr) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado.");
        }

        StatusDoPedido status;
        try {
            status = StatusDoPedido.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new WebApplicationException("Status inválido: " + statusStr, 400);
        }

        pedido.setStatus(status);
    }

    @Transactional
    public void cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado.");
        }

        if (pedido.getStatus() == StatusDoPedido.ENTREGUE ||
            pedido.getStatus() == StatusDoPedido.CANCELADO) {
            throw new WebApplicationException("Não é possível cancelar este pedido.", 400);
        }

        
        for (ItemDoPedido item : pedido.getItens()) {
            Estoque estoque = estoqueRepository.findByMouseId(item.getMouse().getId())
                    .orElseThrow(() -> new NotFoundException("Estoque não encontrado."));
            estoque.setQuantidade(estoque.getQuantidade() + item.getQuantidade());
        }

        pedido.setStatus(StatusDoPedido.CANCELADO);
    }

    @Transactional
    public void deletar(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado.");
        }

       
        for (ItemDoPedido item : pedido.getItens()) {
            Estoque estoque = estoqueRepository.findByMouseId(item.getMouse().getId())
                    .orElse(null);
            if (estoque != null) {
                estoque.setQuantidade(estoque.getQuantidade() + item.getQuantidade());
            }
        }

        if (!pedidoRepository.deleteById(id)) {
            throw new NotFoundException("Pedido não encontrado para deletar.");
        }
    }

    private Long getClienteIdLogado() {
        String username = securityIdentity.getPrincipal().getName();
        return usuarioRepository.getClienteByUsername(username)
                .map(Cliente::getId)
                .orElseThrow(() -> new NotFoundException("Cliente logado não encontrado"));
    }

    private ItemDoPedido processarItemDoPedido(ItemDoPedidoRequestDTO dto, Pedido pedido) {
        Mouse mouse = mouseRepository.findById(dto.getIdMouse());
        if (mouse == null) {
            throw new NotFoundException("Mouse não encontrado.");
        }

        Estoque estoque = estoqueRepository.findByMouseId(mouse.getId())
                .orElseThrow(() -> new NotFoundException("Estoque não encontrado."));

        if (dto.getQuantidade() > estoque.getQuantidade()) {
            throw new WebApplicationException("Estoque insuficiente.", 400);
        }

        estoque.setQuantidade(estoque.getQuantidade() - dto.getQuantidade());

        ItemDoPedido item = new ItemDoPedido();
        item.setPedido(pedido);
        item.setMouse(mouse);
        item.setQuantidade(dto.getQuantidade());
        item.setPrecoUnitario(mouse.getPreco());

        return item;
    }

    private Double calcularValorTotal(List<ItemDoPedido> itens) {
        return itens.stream()
                .mapToDouble(ItemDoPedido::getPrecoTotal)
                .sum();
    }

    private Endereco toEnderecoEntity(EnderecoRequestDTO dto) {
        if (dto == null) return null;

        Endereco e = new Endereco();
        e.setCep(dto.cep());
        e.setRua(dto.rua());
        e.setNumero(dto.numero());
        e.setComplemento(dto.complemento());
        e.setInformacoesAdicionais(dto.informacoesAdicionais());

        return e;
    }

    private PedidoResponseDTO toResponseDTO(Pedido pedido) {
        List<ItemDoPedidoResponseDTO> itensDTO = pedido.getItens().stream()
            .map(item -> {
                Long mouseId = item.getMouse() != null ? item.getMouse().getId() : null;
                String mouseModelo = item.getMouse() != null ? item.getMouse().getModelo() : null;

                return new ItemDoPedidoResponseDTO(
                    item.getId(),
                    pedido.getId(),
                    mouseId,
                    mouseModelo,
                    item.getQuantidade(),
                    item.getPrecoUnitario(),
                    item.getPrecoTotal()
                );
            })
            .collect(Collectors.toList());

        Long clienteId = pedido.getCliente() != null ? pedido.getCliente().getId() : null;
        String clienteNome = pedido.getCliente() != null ? pedido.getCliente().getNome() : null;

        return new PedidoResponseDTO(
                pedido.getId(),
                clienteId,
                clienteNome,
                pedido.getDataPedido(),
                itensDTO,
                pedido.getValorTotal(),
                pedido.getStatus(),
                pedido.getFormaPagamento()
        );
    }
}