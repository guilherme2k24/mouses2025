package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.CorRequestDTO;
import br.unitins.tp1.dto.CorResponseDTO;
import br.unitins.tp1.model.Cor;
import br.unitins.tp1.repository.CorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CorService {

    @Inject
    CorRepository repository;

    public List<CorResponseDTO> findAll() {
        return repository.listAll()
                .stream()
                .map(CorResponseDTO::new)
                .collect(Collectors.toList());
    }

    public CorResponseDTO findById(Long id) {
        Cor cor = repository.findById(id);
        if (cor == null)
            throw new NotFoundException("Cor não encontrada");
        return new CorResponseDTO(cor);
    }

    @Transactional
    public CorResponseDTO create(CorRequestDTO dto) {
        Cor entity = new Cor();
        entity.setNome(dto.getNome());
        repository.persist(entity);
        return new CorResponseDTO(entity);
    }

    @Transactional
    public CorResponseDTO update(Long id, CorRequestDTO dto) {
        Cor entity = repository.findById(id);
        if (entity == null)
            throw new NotFoundException("Cor não encontrada");
        entity.setNome(dto.getNome());
        return new CorResponseDTO(entity);
    }

    @Transactional
    public void delete(Long id) {
        Cor entity = repository.findById(id);
        if (entity == null)
            throw new NotFoundException("Cor não encontrada");
        repository.delete(entity);
    }
}
