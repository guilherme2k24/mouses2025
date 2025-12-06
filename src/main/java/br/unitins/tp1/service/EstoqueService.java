package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.EstoqueRequestDTO;
import br.unitins.tp1.dto.EstoqueResponseDTO;
import br.unitins.tp1.dto.MouseResponseDTO;
import br.unitins.tp1.model.Estoque;
import br.unitins.tp1.model.Mouse;
import br.unitins.tp1.model.Sensor;
import br.unitins.tp1.model.Cor;
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
        Mouse mouse = mousesRepository.findById(dto.getMouseId());
        if (mouse == null) {
            throw new NotFoundException("Mouse não encontrado.");
        }

        Estoque estoque = new Estoque();
        estoque.setQuantidade(dto.getQuantidade());
        estoque.setLocalizacao(dto.getLocalizacao());
        estoque.setMouses(mouse);

        repository.persist(estoque);

        
        MouseResponseDTO mouseDTO = toMouseResponseDTO(mouse);
        return new EstoqueResponseDTO(
            estoque.getId(),
            estoque.getQuantidade(),
            estoque.getLocalizacao(),
            mouseDTO
        );
    }

    public List<EstoqueResponseDTO> listarTodos() {
        return repository.listAll()
            .stream()
            .map(e -> {
                MouseResponseDTO mouseDTO = toMouseResponseDTO(e.getMouses());
                return new EstoqueResponseDTO(
                    e.getId(),
                    e.getQuantidade(),
                    e.getLocalizacao(),
                    mouseDTO
                );
            })
            .collect(Collectors.toList());
    }

    public EstoqueResponseDTO buscarPorId(Long id) {
        Estoque estoque = repository.findById(id);
        if (estoque == null) {
            throw new NotFoundException("Estoque não encontrado.");
        }

        MouseResponseDTO mouseDTO = toMouseResponseDTO(estoque.getMouses());
        return new EstoqueResponseDTO(
            estoque.getId(),
            estoque.getQuantidade(),
            estoque.getLocalizacao(),
            mouseDTO
        );
    }

    @Transactional
    public EstoqueResponseDTO atualizar(Long id, EstoqueRequestDTO dto) {
        Estoque estoque = repository.findById(id);
        if (estoque == null) {
            throw new NotFoundException("Estoque não encontrado.");
        }

        Mouse mouse = mousesRepository.findById(dto.getMouseId());
        if (mouse == null) {
            throw new NotFoundException("Mouse não encontrado.");
        }

        estoque.setQuantidade(dto.getQuantidade());
        estoque.setLocalizacao(dto.getLocalizacao());
        estoque.setMouses(mouse);

        MouseResponseDTO mouseDTO = toMouseResponseDTO(mouse);
        return new EstoqueResponseDTO(
            estoque.getId(),
            estoque.getQuantidade(),
            estoque.getLocalizacao(),
            mouseDTO
        );
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Estoque com ID " + id + " não encontrado.");
        }
    }

    
    private MouseResponseDTO toMouseResponseDTO(Mouse mouse) {
        if (mouse == null) {
            return null;
        }

        
        String sensorNome = mouse.getSensor() != null ? mouse.getSensor().getNome() : null;

        
        List<String> coresNomes = mouse.getCores() != null 
            ? mouse.getCores().stream()
                    .map(Cor::getNome)
                    .collect(Collectors.toList())
            : List.of();

        return new MouseResponseDTO(
            mouse.getId(),
            mouse.getModelo(),
            mouse.getDpi(),
            mouse.getPollingRate(),
            mouse.getBotoes(),
            mouse.getFormato(),
            mouse.getPeso(),
            mouse.getConexao(),
            sensorNome,
            coresNomes,
            mouse.getImageUrl(),
            mouse.getPreco()
        );
    }
}