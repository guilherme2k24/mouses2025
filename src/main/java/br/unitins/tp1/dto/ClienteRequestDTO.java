package br.unitins.tp1.dto;

public class ClienteRequestDTO {
    private String nome;
    private String sobreNome;
    private String idade;
    private String email;

    public ClienteRequestDTO() {
    }

    public ClienteRequestDTO(String email, String idade, String nome, String sobreNome) {
        this.email = email;
        this.idade = idade;
        this.nome = nome;
        this.sobreNome = sobreNome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}