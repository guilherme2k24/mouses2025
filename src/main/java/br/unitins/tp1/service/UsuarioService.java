package br.unitins.tp1.service;

import br.unitins.tp1.dto.UsuarioResponseDTO;

public interface UsuarioService {

    UsuarioResponseDTO findByUsernameAndSenha(String username, String senha);
    UsuarioResponseDTO findByUsername(String username);

    
}