package br.unitins.tp1.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.tp1.dto.FileUploadDTO;
import br.unitins.tp1.service.MinioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.jboss.resteasy.reactive.MultipartForm;

import java.io.InputStream;
import java.util.UUID;

@Path("/upload")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FileUploadResource {

    @Inject
    MinioService minioService;

    @Inject
    JsonWebToken jwt;

    private static final Logger LOG = Logger.getLogger(FileUploadResource.class);

    
    @POST
    @Path("/mouse")
    @RolesAllowed("ADM")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadMouseImage(@MultipartForm FileUploadForm form) {
        try {
            String originalFileName = form.getFileName();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            String uploadedFileName = minioService.uploadFile(
                uniqueFileName, 
                form.getFile(), 
                form.getMimeType(), 
                form.getFileSize()
            );

            String fileUrl = minioService.getFileUrl(uploadedFileName);

            FileUploadDTO response = new FileUploadDTO(
                uploadedFileName, 
                fileUrl,         
                form.getMimeType(), 
                form.getFileSize()
            );

            LOG.infof("Imagem de mouse %s enviada com sucesso", uploadedFileName);
            return Response.ok(response).build();

        } catch (Exception e) {
            LOG.error("Erro no upload da imagem do mouse: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao fazer upload da imagem")
                    .build();
        }
    }

    
    @POST
    @Path("/file")
    @RolesAllowed("ADM")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@MultipartForm FileUploadForm form) {
        try {
            String originalFileName = form.getFileName();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            String uploadedFileName = minioService.uploadFile(
                uniqueFileName, 
                form.getFile(), 
                form.getMimeType(), 
                form.getFileSize()
            );

            String fileUrl = minioService.getFileUrl(uploadedFileName);

            FileUploadDTO response = new FileUploadDTO(
                uploadedFileName, 
                fileUrl,         
                form.getMimeType(), 
                form.getFileSize()
            );

            LOG.infof("Arquivo %s enviado com sucesso para %s", uploadedFileName, form.getTipo());
            return Response.ok(response).build();

        } catch (Exception e) {
            LOG.error("Erro no upload do arquivo: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao fazer upload do arquivo")
                    .build();
        }
    }

    @POST
    @Path("/marca")
    @RolesAllowed("ADM")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadMarcaImage(@MultipartForm FileUploadForm form) {
        try {
            String originalFileName = form.getFileName();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String uniqueFileName = "logo-" + UUID.randomUUID().toString() + fileExtension;

            String uploadedFileName = minioService.uploadFile(
                uniqueFileName, 
                form.getFile(), 
                form.getMimeType(), 
                form.getFileSize()
            );

            String fileUrl = minioService.getFileUrl(uploadedFileName);

            FileUploadDTO response = new FileUploadDTO(
                uploadedFileName, 
                fileUrl,         
                form.getMimeType(), 
                form.getFileSize()
            );

            LOG.infof("Imagem de marca %s enviada com sucesso", uploadedFileName);
            return Response.ok(response).build();

        } catch (Exception e) {
            LOG.error("Erro no upload da imagem de marca: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao fazer upload da imagem")
                    .build();
        }
    }

    @GET
    @Path("/image/{fileName}")
    @Produces({"image/jpeg", "image/png", "image/gif"})
    public Response getImage(@PathParam("fileName") String fileName) {
        try {
            InputStream fileStream = minioService.getFile(fileName);
            return Response.ok(fileStream).build();

        } catch (Exception e) {
            LOG.error("Erro ao buscar imagem: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/image/{fileName}")
    @RolesAllowed({ "ADM" })
    public Response deleteImage(@PathParam("fileName") String fileName) {
        try {
            minioService.deleteFile(fileName);
            return Response.noContent().build();

        } catch (Exception e) {
            LOG.error("Erro ao deletar imagem: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar imagem")
                    .build();
        }
    }
}