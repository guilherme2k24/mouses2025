package br.unitins.tp1.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record FornecedorRequestDTO(

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 2, message = "O nome deve ter pelo menos 2 caracteres.")
    String nome,

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail deve ser válido.")
    String email,

    @NotBlank(message = "O telefone é obrigatório.")
    @Pattern(regexp = "\\d{10,11}", message = "O telefone deve conter 10 ou 11 dígitos.")
    String telefone,

    @NotBlank(message = "O CNPJ é obrigatório.")
    @Pattern(regexp = "\\d{14}", message = "O CNPJ deve conter exatamente 14 dígitos.")
    String cnpj,

    @NotBlank(message = "O nome da empresa é obrigatório.")
    String nomeEmpresa
) {}
