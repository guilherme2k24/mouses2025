package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.CategoriaRequestDTO;
import br.unitins.tp1.dto.CategoriaResponseDTO;
import br.unitins.tp1.model.Categoria;
import br.unitins.tp1.repository.CategoriaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CategoriaService {

    @Inject
    CategoriaRepository repository;

    @Transactional
    public CategoriaResponseDTO salvar(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        repository.persist(categoria);

        return new CategoriaResponseDTO(
            categoria.getId(),
            categoria.getNome()
        );
    }

    public List<CategoriaResponseDTO> listarTodos() {
        return repository.listAll().stream()
            .map(c -> new CategoriaResponseDTO(
                c.getId(),
                c.getNome()))
            .collect(Collectors.toList());
    }

    public CategoriaResponseDTO buscarPorId(Long id) {
        Categoria categoria = repository.findById(id);
        if (categoria == null)
            throw new NotFoundException("Categoria não encontrada.");
        return new CategoriaResponseDTO(
            categoria.getId(),
            categoria.getNome()
        );
    }

    @Transactional
    public void atualizar(Long id, CategoriaRequestDTO dto) {
        Categoria categoria = repository.findById(id);
        if (categoria == null)
            throw new NotFoundException("Categoria não encontrada.");
        categoria.setNome(dto.getNome());
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
