package br.unitins.tp1.dto;

import br.unitins.tp1.model.Cliente;
import br.unitins.tp1.model.ClienteFisico;
import br.unitins.tp1.model.ClienteJuridico;
import br.unitins.tp1.model.Perfil;
import br.unitins.tp1.model.Usuario;

public record UsuarioResponseDTO(
    Long id,
    String nome,
    String username,
    Perfil perfil) {

    public static UsuarioResponseDTO valueOf(Usuario usuario) {
        if (usuario == null)
            return null;

        String nome = null;
        Cliente cliente = usuario.getCliente();

        if (cliente instanceof ClienteFisico fisico) {
            nome = fisico.getNome();
        } else if (cliente instanceof ClienteJuridico juridico) {
            nome = juridico.getNome(); // ou juridico.getRazaoSocial()
        }

        return new UsuarioResponseDTO(
            usuario.getId(),
            nome,
            usuario.getUsername(),
            usuario.getPerfil()
        );
    }
}
