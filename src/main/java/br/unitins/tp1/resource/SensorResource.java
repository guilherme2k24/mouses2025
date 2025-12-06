package br.unitins.tp1.resource;

import java.util.List;

import br.unitins.tp1.dto.SensorRequestDTO;
import br.unitins.tp1.dto.SensorResponseDTO;
import br.unitins.tp1.service.SensorService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/sensors")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SensorResource {

    @Inject
    SensorService service;

    @GET
    public List<SensorResponseDTO> findAll() {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public SensorResponseDTO findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    @RolesAllowed("ADM")
    @Transactional
    public Response create(SensorRequestDTO dto) {
        SensorResponseDTO response = service.create(dto);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADM")
    @Transactional
    public Response update(@PathParam("id") Long id, SensorRequestDTO dto) {
        SensorResponseDTO response = service.update(id, dto);
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
