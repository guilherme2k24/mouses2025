package br.unitins.tp1.resource;

import java.util.List;

import br.unitins.tp1.dto.CorRequestDTO;
import br.unitins.tp1.dto.CorResponseDTO;
import br.unitins.tp1.service.CorService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CorResource {

    @Inject
    CorService service;

    @GET
    @RolesAllowed({"ADM", "USER"})
    public List<CorResponseDTO> findAll() {
        return service.findAll();
    }

    @GET
    @RolesAllowed({"ADM", "USER"})
    @Path("/{id}")
    public CorResponseDTO findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    @RolesAllowed("ADM")
    @Transactional
    public Response create(CorRequestDTO dto) {
        CorResponseDTO response = service.create(dto);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADM")
    @Transactional
    public Response update(@PathParam("id") Long id, CorRequestDTO dto) {
        CorResponseDTO response = service.update(id, dto);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADM")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
