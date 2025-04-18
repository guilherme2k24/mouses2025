package br.unitins.tp1.entity;

import br.unitins.tp1.enums.Conexao;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
public class Mouses {

    @Id
    @GeneratedValue
    private Long id;
    private String modelo;
    private Double dpi;
    private String sensor;
    private Integer pollingRate;
    private Integer botoes;
    private String formato;
    private String cor;
    private Double peso;
    private Conexao conexao;
    // private Boolean fio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getDpi() {
        return dpi;
    }

    public void setDpi(Double dpi) {
        this.dpi = dpi;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public Integer getPollingRate() {
        return pollingRate;
    }

    public void setPollingRate(Integer pollingRate) {
        this.pollingRate = pollingRate;
    }

    public Integer getBotoes() {
        return botoes;
    }

    public void setBotoes(Integer botoes) {
        this.botoes = botoes;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    // public Boolean getFio() {
        //return fio;
    //}
    
    //public void setFio(Boolean fio) {
        //this.fio = fio;
    //}

    public Conexao getConexao() {
        return conexao;
    }

    public void setConexao(Conexao conexao) {
        this.conexao = conexao;
    }
}
