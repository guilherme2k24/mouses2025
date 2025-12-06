package br.unitins.tp1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.tp1.dto.MarcaRequestDTO;
import br.unitins.tp1.dto.MarcaResponseDTO;
import br.unitins.tp1.service.MarcaService;
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

@Path("/marcas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarcaResource {

    private static final Logger LOG = Logger.getLogger(MarcaResource.class);

    @Inject
    MarcaService service;

    @POST
    @RolesAllowed("ADM")
    public Response salvar(@Valid MarcaRequestDTO dto) {
        LOG.infof("Solicitação para salvar nova marca: %s", dto.getNome());
        MarcaResponseDTO responseDTO = service.salvar(dto);
        LOG.infof("Marca criada com ID: %d", responseDTO.getId());
        return Response
                .status(Response.Status.CREATED)
                .entity(responseDTO)
                .build();
    }
    
    @GET
    public List<MarcaResponseDTO> listarTodos() {
        LOG.debug("Listando todas as marcas.");
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorID(@PathParam("id") Long id) {
        LOG.debugf("Buscando marca pelo ID: %d", id);
        try {
            MarcaResponseDTO marca = service.buscarPorId(id);
            return Response.status(Response.Status.OK)
                           .entity(marca)
                           .build();
        } catch (NotFoundException e) {
            LOG.warnf("Marca com ID %d não encontrada.", id);
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Marca não encontrada")
                           .build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response atualizar(@PathParam("id") Long id, @Valid MarcaRequestDTO dto) {
        LOG.infof("Solicitação para atualizar marca ID %d", id);
        try {
            service.atualizar(id, dto);
            LOG.infof("Marca ID %d atualizada com sucesso.", id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warnf("Marca com ID %d não encontrada para atualização.", id);
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Marca não encontrada para atualização")
                           .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response deletar(@PathParam("id") Long id) {
        LOG.infof("Solicitação para deletar marca ID %d", id);
        try {
            service.deletar(id);
            LOG.infof("Marca ID %d deletada com sucesso.", id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warnf("Marca com ID %d não encontrada para exclusão.", id);
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Marca não encontrada para exclusão")
                           .build();
        }
    }
}
