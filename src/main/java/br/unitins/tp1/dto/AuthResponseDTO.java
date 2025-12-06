package br.unitins.tp1.dto;

public record AuthResponseDTO(
    UsuarioResponseDTO usuario,
    String token
) {
}