package br.unitins.tp1.repository;

import java.util.Optional;

import br.unitins.tp1.model.Estoque;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EstoqueRepository implements PanacheRepository<Estoque> {

    public Optional<Estoque> findByMouseId(Long mouseId) {
        return find("mouse.id", mouseId).firstResultOptional();
    }
}
