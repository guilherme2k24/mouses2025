package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.EstoqueRequestDTO;
import br.unitins.tp1.dto.EstoqueResponseDTO;
import br.unitins.tp1.model.Estoque;
import br.unitins.tp1.model.Mouse;
import br.unitins.tp1.repository.EstoqueRepository;
import br.unitins.tp1.repository.MouseRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EstoqueService {

    @Inject
    EstoqueRepository repository;

    @Inject
    MouseRepository mousesRepository;

    @Transactional
    public EstoqueResponseDTO salvar(EstoqueRequestDTO dto) {
        Mouse mouses = mousesRepository.findById(dto.getMouseId());
        if (mouses == null) {
            throw new NotFoundException("Mouse não encontrado.");
        }

        Estoque estoque = new Estoque();
        estoque.setQuantidade(dto.getQuantidade());
        estoque.setLocalizacao(dto.getLocalizacao());
        estoque.setMouses(mouses);

        repository.persist(estoque);

        return new EstoqueResponseDTO(
            estoque.getId(),
            estoque.getQuantidade(),
            estoque.getLocalizacao(),
            estoque.getMouses()
        );
    }

    public List<EstoqueResponseDTO> listarTodos() {
        return repository.listAll()
            .stream()
            .map(e -> new EstoqueResponseDTO(
                e.getId(),
                e.getQuantidade(),
                e.getLocalizacao(),
                e.getMouses()
            ))
            .collect(Collectors.toList());
    }

    public EstoqueResponseDTO buscarPorId(Long id) {
        Estoque estoque = repository.findById(id);
        if (estoque == null) {
            throw new NotFoundException("Estoque não encontrado.");
        }

        return new EstoqueResponseDTO(
            estoque.getId(),
            estoque.getQuantidade(),
            estoque.getLocalizacao(),
            estoque.getMouses()
        );
    }

    @Transactional
    public void atualizar(Long id, EstoqueRequestDTO dto) {
        Estoque estoque = repository.findById(id);
        if (estoque == null) {
            throw new NotFoundException("Estoque não encontrado.");
        }

        Mouse mouses = mousesRepository.findById(dto.getMouseId());
        if (mouses == null) {
            throw new NotFoundException("Mouse não encontrado.");
        }

        estoque.setQuantidade(dto.getQuantidade());
        estoque.setLocalizacao(dto.getLocalizacao());
        estoque.setMouses(mouses);
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Estoque com ID " + id + " não encontrado.");
        }
    }
}
