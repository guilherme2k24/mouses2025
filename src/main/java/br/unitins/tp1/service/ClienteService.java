package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.ClienteRequestDTO;
import br.unitins.tp1.dto.ClienteResponseDTO;
import br.unitins.tp1.entity.Cliente;
import br.unitins.tp1.repository.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ClienteService {

    @Inject
    ClienteRepository repository;

    @Transactional
    public void salvar(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setSobreNome(dto.getSobreNome());
        cliente.setIdade(dto.getIdade());
        cliente.setEmail(dto.getEmail());
        repository.persist(cliente);
    }

    public List<ClienteResponseDTO> listarTodos() {
        return repository.listAll().stream()
                .map(cliente -> new ClienteResponseDTO(
                        cliente.getId(),
                        cliente.getNome(),
                        cliente.getSobreNome(),
                        cliente.getIdade(),
                        cliente.getEmail()))
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = repository.findById(id);
        if (cliente == null) {
            throw new NotFoundException("Marca não encontrada, 404");
        }
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getSobreNome(),
                cliente.getIdade(),
                cliente.getEmail());
    }

    @Transactional
    public void atualizar(Long id, ClienteRequestDTO dto) {
        Cliente cliente = repository.findById(id);
        if (cliente != null) {
            cliente.setNome(dto.getNome());
            cliente.setSobreNome(dto.getSobreNome());
            cliente.setIdade(dto.getIdade());
            cliente.setEmail(dto.getEmail());
        }
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
