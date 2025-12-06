package br.unitins.tp1.repository;

import java.util.Optional;

import br.unitins.tp1.model.Pessoa;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PessoaRepository implements PanacheRepository<Pessoa> {

    public Optional<Pessoa> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
}
