package br.unitins.tp1.repository;

import br.unitins.tp1.model.ClienteFisico;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteFisicoRepository implements PanacheRepository<ClienteFisico> {
}