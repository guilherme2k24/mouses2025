package br.unitins.tp1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity

public class Cliente {
    @Id 
    @GeneratedValue 

    private long id;
    private String nome;
    private String sobreNome;
    private String idade;
    private String email;

    public Cliente() {
    }

    public Cliente(String email, long id, String idade, String nome, String sobreNome) {
        this.email = email;
        this.id = id;
        this.idade = idade;
        this.nome = nome;
        this.sobreNome = sobreNome;
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

