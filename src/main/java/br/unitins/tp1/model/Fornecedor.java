package br.unitins.tp1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Fornecedor extends Pessoa {
    
    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @Column(nullable = false, length = 255)
    private String nomeEmpresa;

    public Fornecedor() {
    }

    public Fornecedor(String nome, String email, String telefone, String cnpj, String nomeEmpresa) {
        super(nome, email, telefone);
        this.cnpj = cnpj;
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }
}
