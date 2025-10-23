package br.unitins.tp1.dto;

public class ClienteResponseDTO {
    private long id;
    private String nome;
    private String sobreNome;
    private String idade;
    private String email;

    public ClienteResponseDTO(long id, String nome, String sobreNome, String idade, String email) {
        this.id = id;
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.idade = idade;
        this.email = email;
    }

    public ClienteResponseDTO() {
    }

    public long getId() {
        return id;
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
