package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.ClienteJuridicoRequestDTO;
import br.unitins.tp1.dto.ClienteJuridicoResponseDTO;
import br.unitins.tp1.model.ClienteJuridico;
import br.unitins.tp1.repository.ClienteJuridicoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ClienteJuridicoService {

    @Inject
    ClienteJuridicoRepository repository;

    @Transactional
    public ClienteJuridicoResponseDTO salvar(ClienteJuridicoRequestDTO dto) {
        ClienteJuridico cliente = new ClienteJuridico();
        cliente.setNome(dto.getNome());
        cliente.setSobreNome(dto.getSobreNome());
        cliente.setIdade(dto.getIdade());
        cliente.setEmail(dto.getEmail());
        cliente.setCnpj(dto.getCnpj());
        repository.persist(cliente);

        return new ClienteJuridicoResponseDTO(
            cliente.getId(),
            cliente.getNome(),
            cliente.getSobreNome(),
            cliente.getIdade(),
            cliente.getEmail(),
            cliente.getCnpj()
        );
    }

    public List<ClienteJuridicoResponseDTO> listarTodos() {
        return repository.listAll().stream()
            .map(cliente -> new ClienteJuridicoResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getSobreNome(),
                cliente.getIdade(),
                cliente.getEmail(),
                cliente.getCnpj()))
            .collect(Collectors.toList());
    }

    public ClienteJuridicoResponseDTO buscarPorId(Long id) {
        ClienteJuridico cliente = repository.findById(id);
        if (cliente == null) {
            throw new NotFoundException("Cliente Jurídico não encontrado");
        }
        return new ClienteJuridicoResponseDTO(
            cliente.getId(),
            cliente.getNome(),
            cliente.getSobreNome(),
            cliente.getIdade(),
            cliente.getEmail(),
            cliente.getCnpj()
        );
    }

    @Transactional
    public void atualizar(Long id, ClienteJuridicoRequestDTO dto) {
        ClienteJuridico cliente = repository.findById(id);
        if (cliente != null) {
            cliente.setNome(dto.getNome());
            cliente.setSobreNome(dto.getSobreNome());
            cliente.setIdade(dto.getIdade());
            cliente.setEmail(dto.getEmail());
            cliente.setCnpj(dto.getCnpj());
        }
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
