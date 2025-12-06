package br.unitins.tp1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.tp1.dto.CategoriaRequestDTO;
import br.unitins.tp1.dto.CategoriaResponseDTO;
import br.unitins.tp1.service.CategoriaService;
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

@Path("/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    private static final Logger LOG = Logger.getLogger(CategoriaResource.class);

    @Inject
    CategoriaService service;

    @POST
    @RolesAllowed("ADM")
    public Response salvar(@Valid CategoriaRequestDTO dto) {
        LOG.infof("Solicitação para salvar nova categoria: %s", dto.getNome());
        CategoriaResponseDTO responseDTO = service.salvar(dto);
        LOG.infof("Categoria criada com ID: %d", responseDTO.getId());
        return Response.status(Response.Status.CREATED).entity(responseDTO).build();
    }

    @GET
    @RolesAllowed({"ADM", "USER"})
    public List<CategoriaResponseDTO> listarTodos() {
        LOG.debug("Listando todas as categorias.");
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorId(@PathParam("id") Long id) {
        LOG.debugf("Buscando categoria pelo ID: %d", id);
        try {
            CategoriaResponseDTO categoria = service.buscarPorId(id);
            return Response.status(Response.Status.OK).entity(categoria).build();
        } catch (NotFoundException e) {
            LOG.warnf("Categoria com ID %d não encontrada.", id);
            return Response.status(Response.Status.NOT_FOUND).entity("Categoria não encontrada").build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response atualizar(@PathParam("id") Long id, @Valid CategoriaRequestDTO dto) {
        LOG.infof("Solicitação para atualizar categoria ID %d com novos dados.", id);
        try {
            service.atualizar(id, dto);
            LOG.infof("Categoria ID %d atualizada com sucesso.", id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warnf("Categoria com ID %d não encontrada para atualização.", id);
            return Response.status(Response.Status.NOT_FOUND).entity("Categoria não encontrada para atualização").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response deletar(@PathParam("id") Long id) {
        LOG.infof("Solicitação para deletar categoria ID: %d", id);
        try {
            service.deletar(id);
            LOG.infof("Categoria ID %d deletada com sucesso.", id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warnf("Categoria com ID %d não encontrada para exclusão.", id);
            return Response.status(Response.Status.NOT_FOUND).entity("Categoria não encontrada para exclusão").build();
        }
    }
}
