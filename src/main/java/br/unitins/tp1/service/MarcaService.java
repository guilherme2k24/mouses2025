package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.MarcaRequestDTO;
import br.unitins.tp1.dto.MarcaResponseDTO;
import br.unitins.tp1.entity.Marca;
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
    public void salvar(MarcaRequestDTO dto){
        Marca marca = new Marca();
        marca.setNome(dto.getNome());
        repository.persist(marca);
    }

    public List<MarcaResponseDTO> listarTodos() {
        return repository.listAll().stream()
            .map(marca -> new MarcaResponseDTO(
                marca.getId(),
                marca.getNome()))
            .collect(Collectors.toList());
    }

    public MarcaResponseDTO buscarPorId(Long id) {
        Marca marca = repository.findById(id);
        if (marca == null) {
            throw new NotFoundException ("Marca não encontrada, 404");
        }
        return new MarcaResponseDTO(
            marca.getId(),
            marca.getNome());
    }

    @Transactional
    public void atualizar(Long id, MarcaRequestDTO dto) {
        Marca marca = repository.findById(id);
        if(marca != null) {
            marca.setNome(dto.getNome());
        }
    }

    @Transactional
    public void deletar(Long id){
        repository.deleteById(id);
    }
}
