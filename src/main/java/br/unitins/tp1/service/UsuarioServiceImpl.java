package br.unitins.tp1.service;

import java.util.Optional;

import br.unitins.tp1.dto.ChangePasswordRequestDTO;
import br.unitins.tp1.dto.UsuarioResponseDTO;
import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository repository;

    @Inject
    HashService hashService;


    @Override
    public UsuarioResponseDTO findByUsernameAndSenha(String username, String senha) {
        System.out.println("=== 🐛 DEBUG USUARIO SERVICE ===");
        System.out.println("Username: " + username);
        System.out.println("Senha hash recebida: " + senha);
        
        Optional<Usuario> usuarioOpt = repository.findByUsername(username);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            System.out.println("Usuário encontrado no BD: " + usuario.getUsername());
            System.out.println("Senha no BD: " + usuario.getSenha());
            System.out.println("Perfil no BD: " + usuario.getPerfil());
            
        
            boolean senhaValida = usuario.getSenha().equals(senha);
            System.out.println("Senha válida? " + senhaValida);
            
            if (senhaValida) {
                System.out.println("✅ AUTENTICAÇÃO BEM SUCEDIDA - Perfil: " + usuario.getPerfil());
                return UsuarioResponseDTO.valueOf(usuario);
            } else {
                System.out.println("❌ SENHA INVÁLIDA");
                System.out.println("🔍 Hash recebido: " + senha);
                System.out.println("🔍 Hash no BD: " + usuario.getSenha());
                System.out.println("🔍 São iguais? " + usuario.getSenha().equals(senha));
            }
        } else {
            System.out.println("❌ USUÁRIO NÃO ENCONTRADO NO BD");
        }
        
        System.out.println("=== FIM DEBUG ===");
        return null;
    }

    @Override
    public UsuarioResponseDTO findByUsername(String username) {
        return repository.findByUsername(username)
            .map(UsuarioResponseDTO::valueOf)
            .orElse(null);
    }

    
    @Override
    @Transactional
    public void trocarSenha(String username, ChangePasswordRequestDTO request) {
        System.out.println("=== 🔐 TROCA DE SENHA ===");
        System.out.println("Usuário: " + username);
        
        
        if (!request.newPassword().equals(request.confirmPassword())) {
            throw new RuntimeException("Nova senha e confirmação não coincidem");
        }

        Optional<Usuario> usuarioOpt = repository.findByUsername(username);
        
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Usuario usuario = usuarioOpt.get();
        
        boolean senhaAtualValida;
        try {
            senhaAtualValida = hashService.verificarSenha(request.currentPassword(), usuario.getSenha());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao verificar senha atual");
        }
        
        System.out.println("Senha atual válida? " + senhaAtualValida);
        
        if (!senhaAtualValida) {
            throw new RuntimeException("Senha atual incorreta");
        }

        
        String novaSenhaHash;
        try {
            novaSenhaHash = hashService.getHashSenha(request.newPassword());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar hash da nova senha");
        }
        
    
        usuario.setSenha(novaSenhaHash);
        repository.persist(usuario);
        
        System.out.println("✅ SENHA ALTERADA COM SUCESSO");
        System.out.println("Novo hash: " + novaSenhaHash);
    }
}