package br.unitins.tp1.dto;

import br.unitins.tp1.enums.Conexao;

public class MousesResponseDTO {

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

    public MousesResponseDTO() {
    }

    public MousesResponseDTO(long id, String modelo, Double dpi, String sensor, Integer pollingRate, Integer botoes, String formato, String cor, Double peso, Conexao conexao) {
        this.id = id;
        this.modelo = modelo;
        this.dpi = dpi;
        this.sensor = sensor;
        this.pollingRate = pollingRate;
        this.botoes = botoes;
        this.formato = formato;
        this.cor = cor;
        this.peso = peso;
        this.conexao = conexao;
    }

    public Long getId() {
        return id;
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

    public Conexao getConexao() {
        return conexao;
    }

    public void setConexao(Conexao conexao) {
        this.conexao = conexao;
    }
}
