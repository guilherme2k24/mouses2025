package br.unitins.tp1.resource;

import java.util.List;

import br.unitins.tp1.dto.ClienteRequestDTO;
import br.unitins.tp1.dto.ClienteResponseDTO;
import br.unitins.tp1.service.ClienteService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    @Context
    SecurityContext securityContext;

    @POST
    @RolesAllowed({"Adm", "User"})
    public Response salvar(@Valid ClienteRequestDTO dto) {
        ClienteResponseDTO responseDTO = clienteService.salvar(dto);
        return Response.status(Response.Status.CREATED).entity(responseDTO).build();
    }

    @GET
    @RolesAllowed({"Adm", "User"})
    public List<ClienteResponseDTO> listar() {
        return clienteService.listarTodos();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Adm", "User"})
    public ClienteResponseDTO buscarPorId(@PathParam("id") Long id) {
        return clienteService.buscarPorId(id);
    }

    @GET
    @Path("/cpf")
    @RolesAllowed({"Adm", "User"})
    public ClienteResponseDTO buscarPorCpf(@QueryParam("cpf") String cpf) {
        return clienteService.buscarPorCpf(cpf);
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Adm", "User"})
    public Response atualizar(@PathParam("id") Long id, @Valid ClienteRequestDTO dto) {
        ClienteResponseDTO atualizado = clienteService.atualizar(id, dto);
        return Response.ok(atualizado).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response deletar(@PathParam("id") Long id) {
        clienteService.deletar(id);
        return Response.noContent().build();
    }
}
