package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.PagamentoRequestDTO;
import br.unitins.tp1.dto.PagamentoResponseDTO;
import br.unitins.tp1.entity.Pagamento;
import br.unitins.tp1.entity.Pedido;
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
    public void salvar(PagamentoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId());
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado");
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setFormaPagamento(dto.getFormaPagamento());
        pagamento.setValor(dto.getValor());
        pagamento.setData(dto.getData());
        pagamento.setPedido(pedido);

        pagamentoRepository.persist(pagamento);
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

        pagamento.setFormaPagamento(dto.getFormaPagamento());
        pagamento.setValor(dto.getValor());
        pagamento.setData(dto.getData());
        pagamento.setPedido(pedido);
    }

    @Transactional
    public void deletar(Long id) {
        pagamentoRepository.deleteById(id);
    }
}
