package br.unitins.tp1.service;

public interface HashService {
    String getHashSenha(String senha) throws Exception;
    boolean verificarSenha(String senhaFornecida, String hashArmazenado); 
}