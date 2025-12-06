package br.unitins.tp1.model;

import br.unitins.tp1.enums.Conexao;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Mouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modelo;
    private Double dpi;

    @Column(name = "pollingrate")
    private Integer pollingRate;

    private Integer botoes;
    private String formato;
    private Double peso;

    @Column(length = 500)
    private String imageUrl; 

    @Column(name = "file_name")
    private String fileName;

    @Column(nullable = false)
    private Double preco;

    @Enumerated(EnumType.STRING)
    private Conexao conexao;

    @ManyToOne
    @JoinColumn(name = "id_sensor")
    private Sensor sensor;

    @ManyToMany
    @JoinTable(
        name = "mouse_cor",
        joinColumns = @JoinColumn(name = "id_mouse"),
        inverseJoinColumns = @JoinColumn(name = "id_cor")
    )
    private List<Cor> cores;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public Double getDpi() { return dpi; }
    public void setDpi(Double dpi) { this.dpi = dpi; }

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

    public Sensor getSensor() { return sensor; }
    public void setSensor(Sensor sensor) { this.sensor = sensor; }

    public List<Cor> getCores() { return cores; }
    public void setCores(List<Cor> cores) { this.cores = cores; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }
}