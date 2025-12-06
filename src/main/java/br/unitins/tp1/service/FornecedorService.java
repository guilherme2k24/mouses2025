package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.FornecedorRequestDTO;
import br.unitins.tp1.dto.FornecedorResponseDTO;
import br.unitins.tp1.model.Fornecedor;
import br.unitins.tp1.repository.FornecedorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class FornecedorService {

    @Inject
    FornecedorRepository fornecedorRepository;

    public List<FornecedorResponseDTO> findAll() {
        return fornecedorRepository.listAll()
                .stream()
                .map(FornecedorResponseDTO::valueOf)
                .toList();
    }

    public FornecedorResponseDTO findById(Long id) {
        Fornecedor fornecedor = fornecedorRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Fornecedor não encontrado"));
        return FornecedorResponseDTO.valueOf(fornecedor);
    }

    @Transactional
    public FornecedorResponseDTO create(FornecedorRequestDTO dto) {
        
        fornecedorRepository.findByCnpj(dto.cnpj()).ifPresent(f -> {
            throw new IllegalArgumentException("CNPJ já cadastrado");
        });

       
        fornecedorRepository.findByEmail(dto.email()).ifPresent(f -> {
            throw new IllegalArgumentException("E-mail já cadastrado");
        });

        Fornecedor fornecedor = new Fornecedor(
            dto.nome(),
            dto.email(),
            dto.telefone(),
            dto.cnpj(),
            dto.nomeEmpresa()
        );

        fornecedorRepository.persist(fornecedor);
        return FornecedorResponseDTO.valueOf(fornecedor);
    }

    @Transactional
    public FornecedorResponseDTO update(Long id, FornecedorRequestDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Fornecedor não encontrado"));

        
        fornecedorRepository.findByCnpj(dto.cnpj()).ifPresent(f -> {
            if (!f.getId().equals(id)) {
                throw new IllegalArgumentException("CNPJ já cadastrado");
            }
        });

        
        fornecedorRepository.findByEmail(dto.email()).ifPresent(f -> {
            if (!f.getId().equals(id)) {
                throw new IllegalArgumentException("E-mail já cadastrado");
            }
        });

        fornecedor.setNome(dto.nome());
        fornecedor.setEmail(dto.email());
        fornecedor.setTelefone(dto.telefone());
        fornecedor.setCnpj(dto.cnpj());
        fornecedor.setNomeEmpresa(dto.nomeEmpresa());

        return FornecedorResponseDTO.valueOf(fornecedor);
    }

    @Transactional
    public void delete(Long id) {
        if (!fornecedorRepository.deleteById(id)) {
            throw new NotFoundException("Fornecedor não encontrado");
        }
    }

    public FornecedorResponseDTO findByCnpj(String cnpj) {
        Fornecedor fornecedor = fornecedorRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new NotFoundException("Fornecedor não encontrado"));
        return FornecedorResponseDTO.valueOf(fornecedor);
    }
}
