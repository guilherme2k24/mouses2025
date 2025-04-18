package br.unitins.tp1.service;


import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.EstoqueRequestDTO;
import br.unitins.tp1.dto.EstoqueResponseDTO;
import br.unitins.tp1.entity.Estoque;
import br.unitins.tp1.entity.Mouses;
import br.unitins.tp1.repository.EstoqueRepository;
import br.unitins.tp1.repository.MousesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EstoqueService {

    @Inject
    EstoqueRepository repository;

    @Inject
    MousesRepository mousesRepository;

    @Transactional
    public void criarEstoque(EstoqueRequestDTO dto) {
        Estoque estoque = new Estoque();
        estoque.setQuantidade(dto.getQuantidade());
        estoque.setLocalizacao(dto.getLocalizacao());

        Mouses mouses = mousesRepository.findById(dto.getMouseId());
        estoque.setMouses(mouses);

        repository.persist(estoque);
    }

    public List<EstoqueResponseDTO> listarTodos() {
        return repository.listAll().stream().map(e -> {
            EstoqueResponseDTO dto = new EstoqueResponseDTO();
            dto.setId(e.getId());
            dto.setQuantidade(e.getQuantidade());
            dto.setLocalizacao(e.getLocalizacao());
            dto.setMouses(e.getMouses());
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void atualizar(Long id, EstoqueRequestDTO dto) {
        Estoque estoque = repository.findById(id);
        estoque.setQuantidade(dto.getQuantidade());
        estoque.setLocalizacao(dto.getLocalizacao());

        Mouses mouses = mousesRepository.findById(dto.getMouseId());
        estoque.setMouses(mouses);
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public EstoqueResponseDTO buscarPorId(Long id) {
        Estoque estoque = repository.findById(id);
        if (estoque == null) {
            throw new NotFoundException("Estoque não disponível, 404");
        }
    
        EstoqueResponseDTO dto = new EstoqueResponseDTO();
        dto.setId(estoque.getId());
        dto.setQuantidade(estoque.getQuantidade());
        dto.setLocalizacao(estoque.getLocalizacao());
        dto.setMouses(estoque.getMouses());
        return dto;
    }
    
}