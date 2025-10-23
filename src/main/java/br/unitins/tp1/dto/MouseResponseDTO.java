package br.unitins.tp1.dto;

import br.unitins.tp1.enums.Conexao;
import java.util.List;

public class MouseResponseDTO {

    private Long id;
    private String modelo;
    private Double dpi;
    private Integer pollingRate;
    private Integer botoes;
    private String formato;
    private Double peso;
    private Conexao conexao;
    private String sensor;
    private List<String> cores;

    public MouseResponseDTO(Long id, String modelo, Double dpi, Integer pollingRate,
                            Integer botoes, String formato, Double peso, Conexao conexao,
                            String sensor, List<String> cores) {
        this.id = id;
        this.modelo = modelo;
        this.dpi = dpi;
        this.pollingRate = pollingRate;
        this.botoes = botoes;
        this.formato = formato;
        this.peso = peso;
        this.conexao = conexao;
        this.sensor = sensor;
        this.cores = cores;
    }

    public Long getId() { return id; }
    public String getModelo() { return modelo; }
    public Double getDpi() { return dpi; }
    public Integer getPollingRate() { return pollingRate; }
    public Integer getBotoes() { return botoes; }
    public String getFormato() { return formato; }
    public Double getPeso() { return peso; }
    public Conexao getConexao() { return conexao; }
    public String getSensor() { return sensor; }
    public List<String> getCores() { return cores; }
}
