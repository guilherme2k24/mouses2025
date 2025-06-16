package br.unitins.tp1.model;

import jakarta.persistence.Entity;

@Entity
public class ClienteFisico extends Cliente {
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}