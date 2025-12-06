package br.unitins.tp1.dto;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import br.unitins.tp1.enums.Conexao;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class MouseRequestDTO {

    @Schema(example = "Logitech G502")
    @NotBlank(message = "O modelo é obrigatório")
    private String modelo;

    @Schema(example = "25600")
    @NotNull(message = "O DPI é obrigatório")
    @Positive(message = "O DPI deve ser um número positivo")
    private Double dpi;

    @Schema(example = "1")
    @NotNull(message = "O ID do sensor é obrigatório")
    private Long idSensor;

    @Schema(example = "[1, 2]")
    @NotNull(message = "É necessário informar ao menos uma cor")
    private List<Long> idsCores;

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

    @Schema(example = "121")
    @NotNull(message = "O peso é obrigatório")
    @Positive(message = "O peso deve ser um valor positivo e em gramas")
    private Double peso;

    @Schema(example = "COM_FIO")
    @NotNull(message = "A conexão é obrigatória")
    private Conexao conexao;

    @Schema(example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890.jpg")
    private String fileName; 

    @Schema(example = "299.90")
    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser positivo")
    private Double preco;

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public Double getDpi() { return dpi; }
    public void setDpi(Double dpi) { this.dpi = dpi; }

    public Long getIdSensor() { return idSensor; }
    public void setIdSensor(Long idSensor) { this.idSensor = idSensor; }

    public List<Long> getIdsCores() { return idsCores; }
    public void setIdsCores(List<Long> idsCores) { this.idsCores = idsCores; }

    public Integer getPollingRate() { return pollingRate; }
    public void setPollingRate(Integer pollingRate) { this.pollingRate = pollingRate; }

    public Integer getBotoes() { return botoes; }
    public void setBotoes(Integer botoes) { this.botoes = botoes; }

    public String getFormato() { return formato; }
    public void setFormato(String formato) { this.formato = formato; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }

    public Conexao getConexao() { return conexao; }
    public void setConexao(Conexao conexao) { this.conexao = conexao; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }
}