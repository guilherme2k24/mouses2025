package br.unitins.tp1.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Cliente extends Pessoa {

    @Column(length = 14, unique = true)
    private String cpf;

    @Column(length = 100, nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    private Perfil perfil = Perfil.USER;

    @ElementCollection
    @CollectionTable(name = "cliente_endereco", joinColumns = @JoinColumn(name = "cliente_id"))
    private List<Endereco> enderecos = new ArrayList<>();

    public Cliente() {}

    public Cliente(String cpf, String senha, String nome, String email, String telefone) {
        super(nome, email, telefone);
        this.cpf = cpf;
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
}
