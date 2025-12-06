package br.unitins.tp1.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.MarcaRequestDTO;
import br.unitins.tp1.dto.MarcaResponseDTO;
import br.unitins.tp1.model.Marca;
import br.unitins.tp1.repository.MarcaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.jboss.logging.Logger;

@ApplicationScoped
public class MarcaService {

    @Inject
    MarcaRepository repository;

    @Inject
    MinioService minioService; 

    private static final Logger LOG = Logger.getLogger(MarcaService.class);

    @Transactional
    public MarcaResponseDTO salvar(MarcaRequestDTO dto){
        LOG.infof("📸 Salvando nova marca: %s", dto.getNome());
        
        Marca marca = new Marca();
        marca.setNome(dto.getNome());
        marca.setFileName(dto.getFileName()); 
        
        
        if (dto.getFileName() != null && !dto.getFileName().isEmpty()) {
            try {
                String imageUrl = minioService.getFileUrl(dto.getFileName());
                marca.setImageUrl(imageUrl);
                LOG.infof("🌐 URL MinIO gerada para marca: %s", imageUrl);
            } catch (Exception e) {
                LOG.error("❌ Erro ao gerar URL MinIO: " + e.getMessage());
                marca.setImageUrl(null);
            }
        } else {
            marca.setImageUrl(null);
        }
        
        repository.persist(marca);

        LOG.infof("💾 Marca salva - ID: %d", marca.getId());
        
        return toResponse(marca);
    }

    public List<MarcaResponseDTO> listarTodos() {
        List<MarcaResponseDTO> marcas = repository.listAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
        LOG.infof("📦 Listando %d marcas", marcas.size());
        return marcas;
    }

    public MarcaResponseDTO buscarPorId(Long id) {
        Marca marca = repository.findById(id);
        if (marca == null) {
            throw new NotFoundException("Marca não encontrada");
        }
        LOG.infof("🔍 Marca encontrada: %d", id);
        return toResponse(marca);
    }

    @Transactional
    public void atualizar(Long id, MarcaRequestDTO dto) {
        Marca marca = repository.findById(id);
        if (marca == null) {
            throw new NotFoundException("Marca não encontrada para atualização");
        }

        
        String fileNameAntigo = marca.getFileName();
        String fileNameNovo = dto.getFileName();
        
        boolean imagemAlterada = fileNameNovo != null && !fileNameNovo.equals(fileNameAntigo);
        
        if (imagemAlterada && fileNameAntigo != null && !fileNameAntigo.isEmpty()) {
            LOG.infof("🔄 Alterando imagem da marca %d: %s -> %s", id, fileNameAntigo, fileNameNovo);
            deletarImagemAntiga(fileNameAntigo);
        }

        marca.setNome(dto.getNome());
        marca.setFileName(dto.getFileName());
        
        
        if (dto.getFileName() != null && !dto.getFileName().isEmpty()) {
            try {
                String imageUrl = minioService.getFileUrl(dto.getFileName());
                marca.setImageUrl(imageUrl);
                LOG.infof("🌐 URL MinIO atualizada: %s", imageUrl);
            } catch (Exception e) {
                LOG.error("❌ Erro ao gerar URL MinIO: " + e.getMessage());
                marca.setImageUrl(null);
            }
        } else {
            marca.setImageUrl(null);
        }
        
        LOG.infof("✏️ Marca atualizada: %d", id);
    }

    @Transactional
    public void deletar(Long id) {
        Marca marca = repository.findById(id);
        if (marca == null) {
            throw new NotFoundException("Marca não encontrada para exclusão");
        }

        
        deletarImagemAntiga(marca.getFileName());
        
        repository.deleteById(id);
        LOG.infof("🗑️ Marca deletada: %d", id);
    }

    
    private MarcaResponseDTO toResponse(Marca marca) {
        String imageUrl = null;
        
        
        if (marca.getFileName() != null && !marca.getFileName().isEmpty()) {
            try {
                imageUrl = minioService.getFileUrl(marca.getFileName());
                LOG.infof("✅ Gerando URL MinIO para marca %d: %s", marca.getId(), imageUrl);
            } catch (Exception e) {
                LOG.error("❌ Erro ao gerar URL MinIO para marca " + marca.getId() + ": " + e.getMessage());
            }
        }
        
        
        if (imageUrl == null && marca.getImageUrl() != null && !marca.getImageUrl().isEmpty()) {
            imageUrl = marca.getImageUrl();
            LOG.infof("⚠️ Usando imageUrl fallback para marca %d", marca.getId());
        }
        
        
        if (imageUrl == null) {
            imageUrl = gerarPlaceholder(marca.getNome());
            LOG.infof("🎨 Usando placeholder para marca %d: %s", marca.getId(), marca.getNome());
        }
        
        return new MarcaResponseDTO(
            marca.getId(),
            marca.getNome(),
            imageUrl
        );
    }

    
    private String gerarPlaceholder(String nome) {
        try {
            String encodedNome = URLEncoder.encode(nome, StandardCharsets.UTF_8.toString());
            return String.format(
                "https://via.placeholder.com/200x100/1e293b/f59e0b?text=%s",
                encodedNome
            );
        } catch (Exception e) {
            return "https://via.placeholder.com/200x100/1e293b/f59e0b?text=Brand+Logo";
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
}