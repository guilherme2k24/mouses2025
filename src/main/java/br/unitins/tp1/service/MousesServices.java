package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.MousesRequestDTO;
import br.unitins.tp1.dto.MousesResponseDTO;
import br.unitins.tp1.entity.Mouses;
import br.unitins.tp1.repository.MousesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class MousesServices {

    @Inject
    MousesRepository repository;

    @Transactional
    public void salvar(MousesRequestDTO dto){
        Mouses mouses = new Mouses();
        mouses.setModelo(dto.getModelo());
        mouses.setDpi(dto.getDpi());
        mouses.setSensor(dto.getSensor());
        mouses.setPollingRate(dto.getPollingRate());
        mouses.setBotoes(dto.getBotoes());
        mouses.setFormato(dto.getFormato());
        mouses.setCor(dto.getCor());
        mouses.setPeso(dto.getPeso());
        mouses.setConexao(dto.getConexao()); // Enum usado aqui
        repository.persist(mouses);
    }

    public List<MousesResponseDTO> listarTodos() {
        return repository.listAll().stream()
              .map(mouses -> new MousesResponseDTO(
                    mouses.getId(),
                    mouses.getModelo(),
                    mouses.getDpi(),
                    mouses.getSensor(),
                    mouses.getPollingRate(),
                    mouses.getBotoes(),
                    mouses.getFormato(),
                    mouses.getCor(),
                    mouses.getPeso(),
                    mouses.getConexao())) // Enum retornado aqui
                .collect(Collectors.toList());
    }

    public MousesResponseDTO buscarPorID(long id) {
        Mouses mouses = repository.findById(id);
        if (mouses == null) {
            throw new NotFoundException("Modelo não encontrado, 404");
        }
        return new MousesResponseDTO(
                    mouses.getId(),
                    mouses.getModelo(),
                    mouses.getDpi(),
                    mouses.getSensor(),
                    mouses.getPollingRate(),
                    mouses.getBotoes(),
                    mouses.getFormato(),
                    mouses.getCor(),
                    mouses.getPeso(),
                    mouses.getConexao());
    }

    @Transactional
    public void atualizar(long id, MousesRequestDTO dto) {
        Mouses mouses = repository.findById(id);
        if(mouses != null){
            mouses.setModelo(dto.getModelo());
            mouses.setDpi(dto.getDpi());
            mouses.setSensor(dto.getSensor());
            mouses.setPollingRate(dto.getPollingRate());
            mouses.setBotoes(dto.getBotoes());
            mouses.setFormato(dto.getFormato());
            mouses.setCor(dto.getCor());
            mouses.setPeso(dto.getPeso());
            mouses.setConexao(dto.getConexao());
        }
    }

    @Transactional
    public void deletar(Long id){
        repository.deleteById(id);
    }
}
