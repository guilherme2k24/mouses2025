package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.ClienteRequestDTO;
import br.unitins.tp1.dto.ClienteResponseDTO;
import br.unitins.tp1.dto.EnderecoRequestDTO;
import br.unitins.tp1.model.Cliente;
import br.unitins.tp1.model.Endereco;
import br.unitins.tp1.model.Perfil;
import br.unitins.tp1.repository.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;

@ApplicationScoped
public class ClienteService {

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    HashService hashService;

    @Transactional
    public ClienteResponseDTO salvar(ClienteRequestDTO dto) {

        if (clienteRepository.findByCpf(dto.cpf()).isPresent()) {
            throw new WebApplicationException("CPF já cadastrado", 400);
        }

        if (clienteRepository.findByEmail(dto.email()).isPresent()) {
            throw new WebApplicationException("Email já cadastrado", 400);
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
        cliente.setCpf(dto.cpf());

        try {
            cliente.setSenha(hashService.getHashSenha(dto.senha()));
        } catch (Exception e) {
            throw new WebApplicationException("Erro ao gerar hash da senha", 500);
        }

        cliente.setPerfil(dto.perfil() != null ? dto.perfil() : Perfil.USER);

        List<Endereco> enderecos = dto.enderecos()
                .stream()
                .map(this::toEnderecoEntity)
                .collect(Collectors.toList());

        cliente.setEnderecos(enderecos);

        clienteRepository.persist(cliente);

        return new ClienteResponseDTO(cliente);
    }

    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.listAll()
                .stream()
                .map(ClienteResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado");
        }
        return new ClienteResponseDTO(cliente);
    }

    public ClienteResponseDTO buscarPorCpf(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado com CPF: " + cpf));
        return new ClienteResponseDTO(cliente);
    }

    public ClienteResponseDTO findByEmail(String email) {
        Cliente cliente = clienteRepository.findByEmail(email).orElse(null);
        return cliente != null ? new ClienteResponseDTO(cliente) : null;
    }

    public boolean validarSenha(String email, String hashSenha) {
        return clienteRepository.findByEmail(email)
                .map(c -> c.getSenha().equals(hashSenha))
                .orElse(false);
    }

    @Transactional
    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {

        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado");
        }

        clienteRepository.findByCpf(dto.cpf()).ifPresent(c -> {
            if (!c.getId().equals(id)) {
                throw new WebApplicationException("CPF já cadastrado para outro cliente", 400);
            }
        });

        clienteRepository.findByEmail(dto.email()).ifPresent(c -> {
            if (!c.getId().equals(id)) {
                throw new WebApplicationException("Email já cadastrado para outro cliente", 400);
            }
        });

        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
        cliente.setCpf(dto.cpf());

        try {
            if (dto.senha() != null && !dto.senha().isBlank()) {
                cliente.setSenha(hashService.getHashSenha(dto.senha()));
            }
        } catch (Exception e) {
            throw new WebApplicationException("Erro ao gerar hash da senha", 500);
        }

        cliente.setPerfil(dto.perfil() != null ? dto.perfil() : cliente.getPerfil());

        List<Endereco> enderecos = dto.enderecos()
                .stream()
                .map(this::toEnderecoEntity)
                .collect(Collectors.toList());

        cliente.setEnderecos(enderecos);

        return new ClienteResponseDTO(cliente);
    }

    @Transactional
    public void deletar(Long id) {
        if (!clienteRepository.deleteById(id)) {
            throw new NotFoundException("Cliente não encontrado");
        }
    }

    private Endereco toEnderecoEntity(EnderecoRequestDTO dto) {
        Endereco endereco = new Endereco();
        endereco.setCep(dto.cep());
        endereco.setRua(dto.rua());
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setInformacoesAdicionais(dto.informacoesAdicionais());
        return endereco;
    }
}