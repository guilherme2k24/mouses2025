package br.unitins.tp1.dto;

import br.unitins.tp1.model.Sensor;

public class SensorResponseDTO {

    private Long id;
    private String nome;

    public SensorResponseDTO() {
    }

    public SensorResponseDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public SensorResponseDTO(Sensor entity) {
        this(entity.getId(), entity.getNome());
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
