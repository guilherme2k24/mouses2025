package br.unitins.tp1.service;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.GetObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@ApplicationScoped
public class MinioService {

    private static final Logger LOG = Logger.getLogger(MinioService.class);

    @Inject
    @ConfigProperty(name = "minio.url")
    String minioUrl;

    @Inject
    @ConfigProperty(name = "minio.access-key")
    String accessKey;

    @Inject
    @ConfigProperty(name = "minio.secret-key")
    String secretKey;

    @Inject
    @ConfigProperty(name = "minio.bucket-name")
    String bucketName;

    private MinioClient minioClient;

    private MinioClient getMinioClient() {
        if (minioClient == null) {
            minioClient = MinioClient.builder()
                    .endpoint(minioUrl)
                    .credentials(accessKey, secretKey)
                    .build();
        }
        return minioClient;
    }

    private void createBucketIfNotExists() {
        try {
            MinioClient client = getMinioClient();
            boolean found = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            
            if (!found) {
                client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                LOG.infof("Bucket %s criado com sucesso", bucketName);
            }
        } catch (Exception e) {
            LOG.error("Erro ao verificar/criar bucket: " + e.getMessage(), e);
            throw new RuntimeException("Erro ao configurar MinIO", e);
        }
    }

    public String uploadFile(String objectName, InputStream inputStream, String contentType, long size) {
        try {
            createBucketIfNotExists();
            
            MinioClient client = getMinioClient();
            
            client.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(inputStream, size, -1)
                    .contentType(contentType)
                    .build()
            );

            LOG.infof("Arquivo %s enviado com sucesso para MinIO", objectName);
            return objectName;

        } catch (Exception e) {
            LOG.error("Erro ao fazer upload do arquivo: " + e.getMessage(), e);
            throw new RuntimeException("Erro ao fazer upload do arquivo", e);
        }
    }

    public InputStream getFile(String objectName) {
        try {
            MinioClient client = getMinioClient();
            
            return client.getObject(
                GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build()
            );

        } catch (Exception e) {
            LOG.error("Erro ao buscar arquivo: " + e.getMessage(), e);
            throw new RuntimeException("Arquivo não encontrado", e);
        }
    }

    public void deleteFile(String objectName) {
        try {
            MinioClient client = getMinioClient();
            
            client.removeObject(
                RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build()
            );

            LOG.infof("Arquivo %s removido com sucesso", objectName);

        } catch (Exception e) {
            LOG.error("Erro ao deletar arquivo: " + e.getMessage(), e);
            throw new RuntimeException("Erro ao deletar arquivo", e);
        }
    }

    public String getFileUrl(String objectName) {
        return String.format("%s/%s/%s", minioUrl, bucketName, objectName);
    }
}