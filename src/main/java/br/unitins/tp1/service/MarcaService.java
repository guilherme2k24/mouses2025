package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.MarcaRequestDTO;
import br.unitins.tp1.dto.MarcaResponseDTO;
import br.unitins.tp1.model.Marca;
import br.unitins.tp1.repository.MarcaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class MarcaService {

    @Inject
    MarcaRepository repository;

    @Transactional
    public MarcaResponseDTO salvar(MarcaRequestDTO dto){
        System.out.println("📸 DTO recebido - nome: " + dto.getNome() + ", imageUrl: " + dto.getImageUrl());
        
        Marca marca = new Marca();
        marca.setNome(dto.getNome());
        marca.setImageUrl(dto.getImageUrl()); // ⭐ LINHA CRÍTICA ADICIONADA
        
        repository.persist(marca);

        System.out.println("💾 Marca salva - id: " + marca.getId() + ", imageUrl: " + marca.getImageUrl());
        
        return new MarcaResponseDTO(
            marca.getId(),
            marca.getNome(),
            marca.getImageUrl()
        );
    }

    public List<MarcaResponseDTO> listarTodos() {
        return repository.listAll().stream()
            .map(marca -> new MarcaResponseDTO(
                marca.getId(),
                marca.getNome(),
                marca.getImageUrl()))
            .collect(Collectors.toList());
    }

    public MarcaResponseDTO buscarPorId(Long id) {
        Marca marca = repository.findById(id);
        if (marca == null) {
            throw new NotFoundException("Marca não encontrada, 404");
        }
        return new MarcaResponseDTO(
            marca.getId(),
            marca.getNome(),
            marca.getImageUrl());
    }

    @Transactional
    public void atualizar(Long id, MarcaRequestDTO dto) {
        Marca marca = repository.findById(id);
        if (marca != null) {
            marca.setNome(dto.getNome());
            marca.setImageUrl(dto.getImageUrl());
        }
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}