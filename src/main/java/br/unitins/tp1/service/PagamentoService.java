package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.PagamentoRequestDTO;
import br.unitins.tp1.dto.PagamentoResponseDTO;
import br.unitins.tp1.model.Pagamento;
import br.unitins.tp1.model.Pedido;
import br.unitins.tp1.repository.PagamentoRepository;
import br.unitins.tp1.repository.PedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PagamentoService {

    @Inject
    PagamentoRepository pagamentoRepository;

    @Inject
    PedidoRepository pedidoRepository;

    @Transactional
    public PagamentoResponseDTO salvar(PagamentoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId());
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado");
        }

        if (pagamentoRepository.findByPedidoId(dto.getPedidoId()) != null) {
            throw new IllegalStateException("Já existe um pagamento para este pedido.");
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setFormaPagamento(dto.getFormaPagamento());
        pagamento.setValor(dto.getValor());
        pagamento.setData(dto.getData());
        pagamento.setPedido(pedido);

        pagamentoRepository.persist(pagamento);

        return new PagamentoResponseDTO(pagamento);
    }

    public List<PagamentoResponseDTO> listarTodos() {
        return pagamentoRepository.listAll().stream()
            .map(PagamentoResponseDTO::new)
            .collect(Collectors.toList());
    }

    public PagamentoResponseDTO buscarPorId(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id);
        if (pagamento == null) {
            throw new NotFoundException("Pagamento não encontrado");
        }
        return new PagamentoResponseDTO(pagamento);
    }

    @Transactional
    public void atualizar(Long id, PagamentoRequestDTO dto) {
        Pagamento pagamento = pagamentoRepository.findById(id);
        if (pagamento == null) {
            throw new NotFoundException("Pagamento não encontrado");
        }

        Pedido pedido = pedidoRepository.findById(dto.getPedidoId());
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado");
        }

        
        Pagamento existente = pagamentoRepository.findByPedidoId(dto.getPedidoId());
        if (existente != null && !existente.getId().equals(id)) {
            throw new IllegalStateException("Este pedido já possui outro pagamento.");
        }

        pagamento.setFormaPagamento(dto.getFormaPagamento());
        pagamento.setValor(dto.getValor());
        pagamento.setData(dto.getData());
        pagamento.setPedido(pedido);
    }

    @Transactional
    public void deletar(Long id) {
        if (!pagamentoRepository.deleteById(id)) {
            throw new NotFoundException("Pagamento não encontrado para exclusão");
        }
    }
}
