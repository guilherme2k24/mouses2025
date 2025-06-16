package br.unitins.tp1.dto;

public class ClienteJuridicoResponseDTO extends ClienteResponseDTO {
    private String cnpj;

    public ClienteJuridicoResponseDTO(Long id, String nome, String sobreNome, String idade, String email, String cnpj) {
        super(id, nome, sobreNome, idade, email);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}