package br.unitins.tp1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.tp1.dto.MousesRequestDTO;
import br.unitins.tp1.dto.MousesResponseDTO;
import br.unitins.tp1.service.MousesServices;
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
public class MousesResource {

    @Inject
    MousesServices services;

    private static final Logger LOG = Logger.getLogger(MousesResource.class);

    @POST
    @RolesAllowed("Adm")
    public Response salvar(@Valid MousesRequestDTO dto) {
        MousesResponseDTO responseDTO = services.salvar(dto);
        LOG.infof("Mouse cadastrado com sucesso: %s", responseDTO.getId());
        return Response.status(Response.Status.CREATED)
                       .entity(responseDTO)
                       .build();
    }

    @GET
    @RolesAllowed({"Adm", "User"})
    public List<MousesResponseDTO> listar() {
        List<MousesResponseDTO> lista = services.listarTodos();
        LOG.infof("Total de mouses listados: %d", lista.size());
        return lista;
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Adm", "User"})
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            MousesResponseDTO dto = services.buscarPorID(id);
            LOG.infof("Mouse encontrado: %d", id);
            return Response.ok(dto).build();
        } catch (NotFoundException e) {
            LOG.warnf("Mouse com id %d não encontrado", id);
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Mouse não encontrado")
                           .build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response atualizar(@PathParam("id") Long id, @Valid MousesRequestDTO dto) {
        try {
            services.atualizar(id, dto);
            LOG.infof("Mouse com id %d atualizado com sucesso", id);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            LOG.warnf("Mouse com id %d não encontrado para atualização", id);
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Mouse não encontrado para atualização")
                           .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response deletar(@PathParam("id") Long id) {
        try {
            services.deletar(id);
            LOG.infof("Mouse com id %d deletado com sucesso", id);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            LOG.warnf("Mouse com id %d não encontrado para exclusão", id);
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Mouse não encontrado para exclusão")
                           .build();
        }
    }
}
