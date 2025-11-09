package br.unitins.tp1.repository;

import java.util.Optional;

import br.unitins.tp1.model.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    public Optional<Cliente> findByCpf(String cpf) {
        return find("cpf", cpf).firstResultOptional();
    }

    public Optional<Cliente> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

    public Optional<Cliente> findByEmailAndSenha(String email, String senha) {
        return find("email = ?1 AND senha = ?2", email, senha).firstResultOptional();
    }
}