package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ClienteJuridicoRequestDTO extends ClienteRequestDTO {
    
    @NotBlank(message = "O CNPJ é obrigatório.")
    @Pattern(
        regexp = "\\d{14}",
        message = "O CNPJ deve conter exatamente 14 dígitos numéricos (apenas números, sem pontos ou traços)."
    )
    private String cnpj;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}