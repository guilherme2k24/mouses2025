package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EnderecoRequestDTO(

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
    String cep,

    @NotBlank(message = "Rua é obrigatória")
    @Size(max = 200, message = "Rua deve ter no máximo 200 caracteres")
    String rua,

    @NotBlank(message = "Número é obrigatório")
    @Size(max = 20, message = "Número deve ter no máximo 20 caracteres")
    String numero,

    @Size(max = 100, message = "Complemento deve ter no máximo 100 caracteres")
    String complemento,

    @Size(max = 255, message = "Informações adicionais deve ter no máximo 255 caracteres")
    String informacoesAdicionais
) {}
