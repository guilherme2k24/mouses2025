package br.unitins.tp1.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Endereco{

    private String cep;
    private String rua;
    private String numero;
    private String complemento;
    private String informacoesAdicionais;

    public Endereco() {}

    public Endereco(String cep, String rua, String numero, String complemento, String informacoesAdicionais) {
        this.cep = cep;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.informacoesAdicionais = informacoesAdicionais;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }
    
    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

       public String getInformacoesAdicionais() {
        return informacoesAdicionais;
    }

    public void setInformacoesAdicionais(String informacoesAdicionais) {
        this.informacoesAdicionais = informacoesAdicionais;
    }
}