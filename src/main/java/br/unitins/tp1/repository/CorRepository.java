package br.unitins.tp1.repository;

import br.unitins.tp1.model.Cor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CorRepository implements PanacheRepository<Cor> {

    public Cor findByNome(String nome) {
        if (nome == null)
            return null;
        return find("UPPER(nome) = ?1", nome.toUpperCase()).firstResult();
    }
}
