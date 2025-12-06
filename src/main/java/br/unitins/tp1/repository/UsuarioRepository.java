package br.unitins.tp1.repository;

import java.util.Optional;

import br.unitins.tp1.model.Cliente;
import br.unitins.tp1.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public Optional<Usuario> findByUsername(String username) {
        return find("username", username).firstResultOptional();
    }

    public Optional<Cliente> getClienteByUsername(String username) {
        return findByUsername(username).map(Usuario::getCliente);
    }
}
