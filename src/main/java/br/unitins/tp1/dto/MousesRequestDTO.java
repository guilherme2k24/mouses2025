package br.unitins.tp1.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import br.unitins.tp1.enums.Conexao;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class MousesRequestDTO {
    
    @Schema(example = "Logitech G502")
    @NotBlank(message = "O modelo é obrigatório")
    private String modelo;

    @Schema(example = "25600")
    @NotNull(message = "O DPI é obrigatório")
    @Positive(message = "O DPI deve ser um número positivo")
    private Double dpi;

    @Schema(example = "HERO_25K")
    @NotBlank(message = "O sensor é obrigatório")
    private String sensor;

    @Schema(example = "1000")
    @NotNull(message = "O polling rate é obrigatório")
    @Min(value = 125, message = "Polling rate mínimo é 125Hz")
    @Max(value = 8000, message = "Polling rate máximo é 8000Hz")
    private Integer pollingRate;

    @Schema(example = "11")
    @NotNull(message = "Número de botões é obrigatório")
    @Min(value = 1, message = "Deve ter pelo menos 1 botão")
    private Integer botoes;

    @Schema(example = "Ergonomico")
    @NotBlank(message = "O formato é obrigatório")
    private String formato;

    @Schema(example = "Preto")
    @NotBlank(message = "A cor é obrigatória")
    private String cor;

    @Schema(example = "121")
    @NotNull(message = "O peso é obrigatório")
    @Positive(message = "O peso deve ser um valor positivo e em gramas")
    private Double peso;

    @Schema(example = "COM_FIO")
    @NotNull(message = "A conexão é obrigatória")
    private Conexao conexao;

    public MousesRequestDTO() {
    }

    public MousesRequestDTO(String modelo, Double dpi, String sensor, Integer pollingRate, Integer botoes, String formato, String cor, Double peso, Conexao conexao) {
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
