package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.SensorRequestDTO;
import br.unitins.tp1.dto.SensorResponseDTO;
import br.unitins.tp1.model.Sensor;
import br.unitins.tp1.repository.SensorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class SensorService {

    @Inject
    SensorRepository repository;

    public List<SensorResponseDTO> findAll() {
        return repository.listAll()
                .stream()
                .map(SensorResponseDTO::new)
                .collect(Collectors.toList());
    }

    public SensorResponseDTO findById(Long id) {
        Sensor sensor = repository.findById(id);
        if (sensor == null)
            throw new NotFoundException("Sensor não encontrado");
        return new SensorResponseDTO(sensor);
    }

    @Transactional
    public SensorResponseDTO create(SensorRequestDTO dto) {
        Sensor entity = new Sensor();
        entity.setNome(dto.getNome());
        repository.persist(entity);
        return new SensorResponseDTO(entity);
    }

    @Transactional
    public SensorResponseDTO update(Long id, SensorRequestDTO dto) {
        Sensor entity = repository.findById(id);
        if (entity == null)
            throw new NotFoundException("Sensor não encontrado");
        entity.setNome(dto.getNome());
        return new SensorResponseDTO(entity);
    }

    @Transactional
    public void delete(Long id) {
        Sensor entity = repository.findById(id);
        if (entity == null)
            throw new NotFoundException("Sensor não encontrado");
        repository.delete(entity);
    }
}
