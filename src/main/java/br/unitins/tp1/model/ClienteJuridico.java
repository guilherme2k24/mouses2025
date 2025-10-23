package br.unitins.tp1.model;

import jakarta.persistence.Entity;

@Entity
public class ClienteJuridico extends Cliente {
    private String cnpj;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}