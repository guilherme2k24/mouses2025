package br.unitins.tp1.service;

import java.util.Optional;

import br.unitins.tp1.dto.UsuarioResponseDTO;
import br.unitins.tp1.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository repository;

    @Override
    public UsuarioResponseDTO findByUsernameAndSenha(String username, String senha) {


        Optional<UsuarioResponseDTO> usuarioOpt = repository.findByUsername(username)
            .filter(usuario -> usuario.getSenha().equals(senha))
            .map(UsuarioResponseDTO::valueOf);

        return usuarioOpt.orElse(null);
    }

    @Override
    public UsuarioResponseDTO findByUsername(String username) {
        return repository.findByUsername(username)
            .map(UsuarioResponseDTO::valueOf)
            .orElse(null);
    }
}
