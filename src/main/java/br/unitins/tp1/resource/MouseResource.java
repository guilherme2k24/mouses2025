package br.unitins.tp1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.tp1.dto.MouseRequestDTO;
import br.unitins.tp1.dto.MouseResponseDTO;
import br.unitins.tp1.service.MouseService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/mouses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MouseResource {

    @Inject
    MouseService service;

    private static final Logger LOG = Logger.getLogger(MouseResource.class);

    @POST
    @RolesAllowed("ADM")
    public Response salvar(@Valid MouseRequestDTO dto) {
        MouseResponseDTO responseDTO = service.salvar(dto);
        LOG.infof("Mouse cadastrado com sucesso: %s", responseDTO.getId());
        return Response.status(Response.Status.CREATED)
                .entity(responseDTO)
                .build();
    }

    @GET
    public List<MouseResponseDTO> listar() {
        List<MouseResponseDTO> lista = service.listarTodos();
        LOG.infof("Total de mouses listados: %d", lista.size());
        return lista;
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            MouseResponseDTO dto = service.buscarPorID(id);
            LOG.infof("Mouse encontrado: %d", id);
            return Response.ok(dto).build();
        } catch (NotFoundException e) {
            LOG.warnf("Mouse com id %d não encontrado", id);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @RolesAllowed("ADM")
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid MouseRequestDTO dto) {
        try {
            service.atualizar(id, dto);
            LOG.infof("Mouse com id %d atualizado com sucesso", id);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            LOG.warnf("Mouse com id %d não encontrado para atualização", id);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @DELETE
    @RolesAllowed("ADM")
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        try {
            service.deletar(id);
            LOG.infof("Mouse com id %d deletado com sucesso", id);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            LOG.warnf("Mouse com id %d não encontrado para exclusão", id);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
