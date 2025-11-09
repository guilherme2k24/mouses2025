package br.unitins.tp1.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.MouseRequestDTO;
import br.unitins.tp1.dto.MouseResponseDTO;
import br.unitins.tp1.model.Cor;
import br.unitins.tp1.model.Mouse;
import br.unitins.tp1.model.Sensor;
import br.unitins.tp1.repository.CorRepository;
import br.unitins.tp1.repository.MouseRepository;
import br.unitins.tp1.repository.SensorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class MouseService {

    @Inject
    MouseRepository mouseRepository;

    @Inject
    SensorRepository sensorRepository;

    @Inject
    CorRepository corRepository;

    @Transactional
    public MouseResponseDTO salvar(MouseRequestDTO dto) {
        Mouse mouse = new Mouse();
        atualizarDadosMouse(mouse, dto);
        mouseRepository.persist(mouse);
        return toResponse(mouse);
    }

    public List<MouseResponseDTO> listarTodos() {
        return mouseRepository.listAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public MouseResponseDTO buscarPorID(long id) {
        Mouse mouse = mouseRepository.findById(id);
        if (mouse == null) {
            throw new NotFoundException("Mouse não encontrado.");
        }
        return toResponse(mouse);
    }

    @Transactional
    public void atualizar(long id, MouseRequestDTO dto) {
        Mouse mouse = mouseRepository.findById(id);
        if (mouse == null) {
            throw new NotFoundException("Mouse não encontrado para atualização.");
        }
        atualizarDadosMouse(mouse, dto);
    }

    @Transactional
    public void deletar(Long id) {
        boolean deleted = mouseRepository.deleteById(id);
        if (!deleted) {
            throw new NotFoundException("Mouse não encontrado para exclusão.");
        }
    }

    private void atualizarDadosMouse(Mouse mouse, MouseRequestDTO dto) {
        mouse.setModelo(dto.getModelo());
        mouse.setDpi(dto.getDpi());
        mouse.setPollingRate(dto.getPollingRate());
        mouse.setBotoes(dto.getBotoes());
        mouse.setFormato(dto.getFormato());
        mouse.setPeso(dto.getPeso());
        mouse.setConexao(dto.getConexao());
        mouse.setImageUrl(dto.getImageUrl());
        mouse.setPreco(dto.getPreco()); // ✅ adiciona o preço

        Sensor sensor = sensorRepository.findById(dto.getIdSensor());
        if (sensor == null) {
            throw new NotFoundException("Sensor com ID " + dto.getIdSensor() + " não encontrado.");
        }
        mouse.setSensor(sensor);

        List<Cor> cores = dto.getIdsCores().stream()
                .map(idCor -> {
                    Cor cor = corRepository.findById(idCor);
                    if (cor == null) {
                        throw new NotFoundException("Cor com ID " + idCor + " não encontrada.");
                    }
                    return cor;
                })
                .collect(Collectors.toList());
        mouse.setCores(cores);
    }

    private MouseResponseDTO toResponse(Mouse mouse) {
        return new MouseResponseDTO(
                mouse.getId(),
                mouse.getModelo(),
                mouse.getDpi(),
                mouse.getPollingRate(),
                mouse.getBotoes(),
                mouse.getFormato(),
                mouse.getPeso(),
                mouse.getConexao(),
                mouse.getSensor() != null ? mouse.getSensor().getNome() : null,
                mouse.getCores() != null
                        ? mouse.getCores().stream().map(Cor::getNome).collect(Collectors.toList())
                        : Collections.emptyList(),
                mouse.getImageUrl(),
                mouse.getPreco() // ✅ inclui o preço no DTO de resposta
        );
    }
}
