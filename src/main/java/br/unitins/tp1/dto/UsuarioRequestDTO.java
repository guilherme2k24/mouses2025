package br.unitins.tp1.dto;

import br.unitins.tp1.model.Perfil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuarioRequestDTO {

    @NotBlank(message= "username nao pode ser nulo")
    private String username;

    @NotBlank(message= "senha nao pode ser nulo")
    private String senha;

    @NotNull(message= "perfil nao pode ser nulo")
    private Perfil perfil;

    @NotNull(message= "id cliente nao pode ser nulo")
    private Long idCliente;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }
}
