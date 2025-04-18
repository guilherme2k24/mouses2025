package br.unitins.tp1.repository;

import br.unitins.tp1.entity.Mouses;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MousesRepository implements PanacheRepository<Mouses>{
    
}
