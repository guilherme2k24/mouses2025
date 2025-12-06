package br.unitins.tp1.dto;

import br.unitins.tp1.model.Cor;

public class CorResponseDTO {

    private Long id;
    private String nome;

    public CorResponseDTO() {
    }

    public CorResponseDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public CorResponseDTO(Cor entity) {
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
