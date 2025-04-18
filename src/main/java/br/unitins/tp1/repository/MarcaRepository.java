package br.unitins.tp1.repository;

import br.unitins.tp1.entity.Marca;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MarcaRepository implements PanacheRepository <Marca> {

}
