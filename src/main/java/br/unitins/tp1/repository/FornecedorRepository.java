package br.unitins.tp1.repository;

import java.util.Optional;

import br.unitins.tp1.model.Fornecedor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FornecedorRepository implements PanacheRepository<Fornecedor> {

    public Optional<Fornecedor> findByCnpj(String cnpj) {
        return find("cnpj", cnpj).firstResultOptional();
    }

    public Optional<Fornecedor> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

    public Optional<Fornecedor> findByNomeEmpresa(String nomeEmpresa) {
        return find("nomeEmpresa", nomeEmpresa).firstResultOptional();
    }
}
