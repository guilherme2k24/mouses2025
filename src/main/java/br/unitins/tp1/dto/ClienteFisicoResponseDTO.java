package br.unitins.tp1.dto;

public class ClienteFisicoResponseDTO extends ClienteResponseDTO {
    private String cpf;

    public ClienteFisicoResponseDTO(Long id, String nome, String sobreNome, String idade, String email, String cpf) {
        super(id, nome, sobreNome, idade, email);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}