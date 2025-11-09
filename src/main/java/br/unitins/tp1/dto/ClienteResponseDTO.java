package br.unitins.tp1.dto;

import java.util.List;

import br.unitins.tp1.model.Cliente;
import br.unitins.tp1.model.Endereco;
import br.unitins.tp1.model.Perfil;

public record ClienteResponseDTO(
    Long id,
    String nome,
    String email,
    String telefone,
    String cpf,
    Perfil perfil,
    List<Endereco> enderecos
) {

    public ClienteResponseDTO(Cliente cliente) {
        this(
            cliente.getId(),
            cliente.getNome(),
            cliente.getEmail(),
            cliente.getTelefone(),
            cliente.getCpf(),
            cliente.getPerfil(),
            cliente.getEnderecos()
        );
    }
}
