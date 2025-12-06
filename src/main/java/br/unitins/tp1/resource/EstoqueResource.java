package br.unitins.tp1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.tp1.dto.EstoqueRequestDTO;
import br.unitins.tp1.dto.EstoqueResponseDTO;
import br.unitins.tp1.service.EstoqueService;
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

@Path("/estoques")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EstoqueResource {

    private static final Logger LOG = Logger.getLogger(EstoqueResource.class);

    @Inject
    EstoqueService service;

    @POST
    @RolesAllowed("ADM")
    public Response salvar(@Valid EstoqueRequestDTO dto) {
        LOG.infof("Solicitação para salvar novo estoque: %s", dto);
        EstoqueResponseDTO responseDTO = service.salvar(dto);
        LOG.infof("Estoque criado com ID: %d", responseDTO.getId());
        return Response.status(Response.Status.CREATED)
                       .entity(responseDTO)
                       .build();
    }

    @GET
    @RolesAllowed({"ADM", "USER"})
    public List<EstoqueResponseDTO> listar() {
        LOG.debug("Listando todos os estoques.");
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorId(@PathParam("id") Long id) {
        LOG.debugf("Buscando estoque pelo ID: %d", id);
        try {
            EstoqueResponseDTO dto = service.buscarPorId(id);
            return Response.ok(dto).build();
        } catch (NotFoundException e) {
            LOG.warnf("Estoque com ID %d não encontrado.", id);
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Estoque não encontrado")
                           .build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response atualizar(@PathParam("id") Long id, @Valid EstoqueRequestDTO dto) {
        LOG.infof("Solicitação para atualizar estoque ID %d", id);
        try {
            service.atualizar(id, dto);
            LOG.infof("Estoque ID %d atualizado com sucesso.", id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warnf("Estoque com ID %d não encontrado para atualização.", id);
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Estoque não encontrado para atualização")
                           .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response deletar(@PathParam("id") Long id) {
        LOG.infof("Solicitação para deletar estoque ID: %d", id);
        try {
            service.deletar(id);
            LOG.infof("Estoque ID %d deletado com sucesso.", id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warnf("Estoque com ID %d não encontrado para exclusão.", id);
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Estoque não encontrado para exclusão")
                           .build();
        }
    }
}
