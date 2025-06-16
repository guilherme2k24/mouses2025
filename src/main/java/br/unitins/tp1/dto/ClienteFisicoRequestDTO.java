package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ClienteFisicoRequestDTO extends ClienteRequestDTO {
    
    @NotBlank(message = "O CPF é obrigatório.")
    @Pattern(
        regexp = "\\d{11}",
        message = "O CPF deve conter exatamente 11 dígitos numéricos (apenas números, sem pontos ou traços)."
    )    
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}