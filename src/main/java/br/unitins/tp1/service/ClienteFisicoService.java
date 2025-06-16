package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.ClienteFisicoRequestDTO;
import br.unitins.tp1.dto.ClienteFisicoResponseDTO;
import br.unitins.tp1.model.ClienteFisico;
import br.unitins.tp1.repository.ClienteFisicoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ClienteFisicoService {

    @Inject
    ClienteFisicoRepository repository;

    @Transactional
    public ClienteFisicoResponseDTO salvar(ClienteFisicoRequestDTO dto) {
        ClienteFisico cliente = new ClienteFisico();
        cliente.setNome(dto.getNome());
        cliente.setSobreNome(dto.getSobreNome());
        cliente.setIdade(dto.getIdade());
        cliente.setEmail(dto.getEmail());
        cliente.setCpf(dto.getCpf());
        repository.persist(cliente);

        return new ClienteFisicoResponseDTO(
            cliente.getId(),
            cliente.getNome(),
            cliente.getSobreNome(),
            cliente.getIdade(),
            cliente.getEmail(),
            cliente.getCpf()
        );
    }

    public List<ClienteFisicoResponseDTO> listarTodos() {
        return repository.listAll().stream()
            .map(cliente -> new ClienteFisicoResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getSobreNome(),
                cliente.getIdade(),
                cliente.getEmail(),
                cliente.getCpf()))
            .collect(Collectors.toList());
    }

    public ClienteFisicoResponseDTO buscarPorId(Long id) {
        ClienteFisico cliente = repository.findById(id);
        if (cliente == null) {
            throw new NotFoundException("Cliente Físico não encontrado");
        }
        return new ClienteFisicoResponseDTO(
            cliente.getId(),
            cliente.getNome(),
            cliente.getSobreNome(),
            cliente.getIdade(),
            cliente.getEmail(),
            cliente.getCpf()
        );
    }

    @Transactional
    public void atualizar(Long id, ClienteFisicoRequestDTO dto) {
        ClienteFisico cliente = repository.findById(id);
        if (cliente == null) {
            throw new NotFoundException("Cliente Físico não encontrado");
        }
        cliente.setNome(dto.getNome());
        cliente.setSobreNome(dto.getSobreNome());
        cliente.setIdade(dto.getIdade());
        cliente.setEmail(dto.getEmail());
        cliente.setCpf(dto.getCpf());
    }

    @Transactional
    public void deletar(Long id) {
        boolean deleted = repository.deleteById(id);
        if (!deleted) {
            throw new NotFoundException("Cliente Físico não encontrado");
        }
    }
}
