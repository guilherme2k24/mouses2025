package br.unitins.tp1.dto;

import br.unitins.tp1.model.Fornecedor;

public record FornecedorResponseDTO(
    Long id,
    String nome,
    String email,
    String telefone,
    String cnpj,
    String nomeEmpresa
) {
    public static FornecedorResponseDTO valueOf(Fornecedor fornecedor) {
        return new FornecedorResponseDTO(
            fornecedor.getId(),
            fornecedor.getNome(),
            fornecedor.getEmail(),
            fornecedor.getTelefone(),
            fornecedor.getCnpj(),
            fornecedor.getNomeEmpresa()
        );
    }
}
