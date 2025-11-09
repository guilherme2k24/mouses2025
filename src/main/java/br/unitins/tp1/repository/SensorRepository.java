package br.unitins.tp1.repository;

import br.unitins.tp1.model.Sensor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SensorRepository implements PanacheRepository<Sensor> {

    public Sensor findByNome(String nome) {
        if (nome == null)
            return null;
        return find("UPPER(nome) = ?1", nome.toUpperCase()).firstResult();
    }
}
