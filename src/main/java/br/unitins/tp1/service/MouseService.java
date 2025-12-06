package br.unitins.tp1.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
import org.jboss.logging.Logger;

@ApplicationScoped
public class MouseService {

    @Inject
    MouseRepository mouseRepository;

    @Inject
    SensorRepository sensorRepository;

    @Inject
    CorRepository corRepository;

    @Inject
    MinioService minioService;

    private static final Logger LOG = Logger.getLogger(MouseService.class);

    @Transactional
    public MouseResponseDTO salvar(MouseRequestDTO dto) {
        Mouse mouse = new Mouse();
        atualizarDadosMouse(mouse, dto);
        mouseRepository.persist(mouse);
        LOG.infof("✅ Mouse criado com ID: %d", mouse.getId());
        return toResponse(mouse);
    }

    public List<MouseResponseDTO> listarTodos() {
        List<MouseResponseDTO> mouses = mouseRepository.listAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        LOG.infof("📦 Listando %d mouses", mouses.size());
        return mouses;
    }

    public MouseResponseDTO buscarPorID(long id) {
        Mouse mouse = mouseRepository.findById(id);
        if (mouse == null) {
            throw new NotFoundException("Mouse não encontrado.");
        }
        LOG.infof("🔍 Mouse encontrado: %d", id);
        return toResponse(mouse);
    }

    @Transactional
    public void atualizar(long id, MouseRequestDTO dto) {
        Mouse mouse = mouseRepository.findById(id);
        if (mouse == null) {
            throw new NotFoundException("Mouse não encontrado para atualização.");
        }

        
        String fileNameAntigo = mouse.getFileName();
        String fileNameNovo = dto.getFileName();
        
        boolean imagemAlterada = fileNameNovo != null && !fileNameNovo.equals(fileNameAntigo);
        
        if (imagemAlterada && fileNameAntigo != null && !fileNameAntigo.isEmpty()) {
            LOG.infof("🔄 Alterando imagem do mouse %d: %s -> %s", id, fileNameAntigo, fileNameNovo);
            deletarImagemAntiga(fileNameAntigo);
        }
        
        atualizarDadosMouse(mouse, dto);
        LOG.infof("✏️ Mouse atualizado: %d", id);
    }

    @Transactional
    public void deletar(Long id) {
        Mouse mouse = mouseRepository.findById(id);
        if (mouse == null) {
            throw new NotFoundException("Mouse não encontrado para exclusão.");
        }

       
        deletarImagemAntiga(mouse.getFileName());
        
        boolean deleted = mouseRepository.deleteById(id);
        if (!deleted) {
            throw new NotFoundException("Erro ao excluir mouse.");
        }
        LOG.infof("🗑️ Mouse deletado: %d", id);
    }

    private void atualizarDadosMouse(Mouse mouse, MouseRequestDTO dto) {
        mouse.setModelo(dto.getModelo());
        mouse.setDpi(dto.getDpi());
        mouse.setPollingRate(dto.getPollingRate());
        mouse.setBotoes(dto.getBotoes());
        mouse.setFormato(dto.getFormato());
        mouse.setPeso(dto.getPeso());
        mouse.setConexao(dto.getConexao());
        mouse.setPreco(dto.getPreco());

        
        mouse.setFileName(dto.getFileName());
    
        
        if (dto.getFileName() != null && !dto.getFileName().isEmpty()) {
            try {
                String imageUrl = minioService.getFileUrl(dto.getFileName());
                mouse.setImageUrl(imageUrl);
                LOG.infof("🌐 URL MinIO gerada para %s: %s", dto.getFileName(), imageUrl);
            } catch (Exception e) {
                LOG.error("❌ Erro ao gerar URL do MinIO para " + dto.getFileName() + ": " + e.getMessage());
                mouse.setImageUrl(null);
            }
        } else {
            mouse.setImageUrl(null);
        }

        
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
        String imageUrl = null;
        
        
        if (mouse.getFileName() != null && !mouse.getFileName().isEmpty()) {
            try {
                imageUrl = minioService.getFileUrl(mouse.getFileName());
                LOG.infof("✅ Gerando URL MinIO para mouse %d: %s", mouse.getId(), imageUrl);
            } catch (Exception e) {
                LOG.error("❌ Erro ao gerar URL MinIO para mouse " + mouse.getId() + ": " + e.getMessage());
            }
        }
        
        
        if (imageUrl == null && mouse.getImageUrl() != null && !mouse.getImageUrl().isEmpty()) {
            imageUrl = mouse.getImageUrl();
            LOG.infof("⚠️ Usando imageUrl fallback para mouse %d", mouse.getId());
        }
        
        
        if (imageUrl == null) {
            imageUrl = gerarPlaceholder(mouse.getModelo());
            LOG.infof("🎨 Usando placeholder para mouse %d: %s", mouse.getId(), mouse.getModelo());
        }
        
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
                imageUrl, 
                mouse.getPreco()
        );
    }

    
    private String gerarPlaceholder(String modelo) {
        try {
            String encodedModelo = URLEncoder.encode(modelo, StandardCharsets.UTF_8.toString());
            return String.format(
                "https://via.placeholder.com/400x300/1e293b/f59e0b?text=%s",
                encodedModelo
            );
        } catch (Exception e) {
            return "https://via.placeholder.com/400x300/1e293b/f59e0b?text=Gaming+Mouse";
        }
    }

    
    private void deletarImagemAntiga(String fileName) {
        if (fileName != null && !fileName.isEmpty()) {
            try {
                minioService.deleteFile(fileName);
                LOG.infof("🗑️ Imagem deletada do MinIO: %s", fileName);
            } catch (Exception e) {
                LOG.error("⚠️ Erro ao deletar imagem do MinIO: " + fileName + " - " + e.getMessage());
                
            }
        }
    }

    
    public List<MouseResponseDTO> buscarPorModelo(String modelo) {
        List<Mouse> mouses = mouseRepository.find("modelo like ?1", "%" + modelo + "%").list();
        LOG.infof("🔍 Busca por modelo '%s': %d resultados", modelo, mouses.size());
        return mouses.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}