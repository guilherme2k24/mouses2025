package br.unitins.tp1.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ClienteRequestDTO {


    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 2, message = "O nome deve ter pelo menos 2 caracteres.")
    @Schema(example = "João")
    private String nome;

    @NotBlank(message = "O sobrenome é obrigatório.")
    @Size(min = 2, message = "O sobrenome deve ter pelo menos 2 caracteres.")
    @Schema(example = "Alves")
    private String sobreNome;

    @NotBlank(message = "A idade é obrigatória.")
    @Pattern(regexp = "\\d{1,3}", message = "A idade deve conter apenas números (até 3 dígitos).")
    @Schema(example = "23")
    private String idade;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail deve ser válido.")
    @Schema(example = "joaoalv@gmail.com")
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