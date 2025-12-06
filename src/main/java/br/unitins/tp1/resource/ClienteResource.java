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
    @RolesAllowed({"ADM", "USER"})
    public Response salvar(@Valid ClienteRequestDTO dto) {
        ClienteResponseDTO responseDTO = clienteService.salvar(dto);
        return Response.status(Response.Status.CREATED).entity(responseDTO).build();
    }

    @GET
    @RolesAllowed("ADM") 
    public List<ClienteResponseDTO> listar() {
        return clienteService.listarTodos();
    }

    @GET
    @Path("/me") 
    @RolesAllowed({"ADM", "USER"})
    public Response buscarMeusDados() {
        try {
            String email = securityContext.getUserPrincipal().getName();
            ClienteResponseDTO cliente = clienteService.findByEmail(email);
            
            if (cliente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("Cliente não encontrado para o usuário logado")
                    .build();
            }
            
            return Response.ok(cliente).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Erro ao buscar dados do cliente: " + e.getMessage())
                .build();
        }
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            
            if (!securityContext.isUserInRole("ADM")) {
                String email = securityContext.getUserPrincipal().getName();
                ClienteResponseDTO clienteLogado = clienteService.findByEmail(email);
                
                if (clienteLogado == null || !clienteLogado.id().equals(id)) {
                    return Response.status(Response.Status.FORBIDDEN)
                        .entity("Você não tem permissão para acessar este cliente")
                        .build();
                }
            }
            
            ClienteResponseDTO cliente = clienteService.buscarPorId(id);
            return Response.ok(cliente).build();
            
        } catch (jakarta.ws.rs.NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(e.getMessage())
                .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Erro ao buscar cliente: " + e.getMessage())
                .build();
        }
    }

    @GET
    @Path("/cpf")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorCpf(@QueryParam("cpf") String cpf) {
        try {
            
            if (!securityContext.isUserInRole("ADM")) {
                String email = securityContext.getUserPrincipal().getName();
                ClienteResponseDTO clienteLogado = clienteService.findByEmail(email);
                
                if (clienteLogado == null || !clienteLogado.cpf().equals(cpf)) {
                    return Response.status(Response.Status.FORBIDDEN)
                        .entity("Você só pode buscar seus próprios dados por CPF")
                        .build();
                }
            }
            
            ClienteResponseDTO cliente = clienteService.buscarPorCpf(cpf);
            return Response.ok(cliente).build();
            
        } catch (jakarta.ws.rs.NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(e.getMessage())
                .build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"ADM", "USER"})
    public Response atualizar(@PathParam("id") Long id, @Valid ClienteRequestDTO dto) {
        try {
            
            if (!securityContext.isUserInRole("ADM")) {
                String email = securityContext.getUserPrincipal().getName();
                ClienteResponseDTO clienteLogado = clienteService.findByEmail(email);
                
                if (clienteLogado == null || !clienteLogado.id().equals(id)) {
                    return Response.status(Response.Status.FORBIDDEN)
                        .entity("Você só pode atualizar seus próprios dados")
                        .build();
                }
            }
            
            ClienteResponseDTO atualizado = clienteService.atualizar(id, dto);
            return Response.ok(atualizado).build();
            
        } catch (jakarta.ws.rs.NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(e.getMessage())
                .build();
        } catch (jakarta.ws.rs.WebApplicationException e) {
            return Response.status(e.getResponse().getStatus())
                .entity(e.getMessage())
                .build();
        }
    }

    @PUT
    @Path("/me") 
    @RolesAllowed({"ADM", "USER"})
    public Response atualizarMeusDados(@Valid ClienteRequestDTO dto) {
        try {
            String email = securityContext.getUserPrincipal().getName();
            ClienteResponseDTO clienteLogado = clienteService.findByEmail(email);
            
            if (clienteLogado == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("Cliente não encontrado")
                    .build();
            }
            
            ClienteResponseDTO atualizado = clienteService.atualizar(clienteLogado.id(), dto);
            return Response.ok(atualizado).build();
            
        } catch (jakarta.ws.rs.WebApplicationException e) {
            return Response.status(e.getResponse().getStatus())
                .entity(e.getMessage())
                .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Erro ao atualizar cliente: " + e.getMessage())
                .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response deletar(@PathParam("id") Long id) {
        try {
            clienteService.deletar(id);
            return Response.noContent().build();
        } catch (jakarta.ws.rs.NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(e.getMessage())
                .build();
        }
    }

    @DELETE
    @Path("/me") 
    @RolesAllowed("USER")
    public Response deletarMinhaConta() {
        try {
            String email = securityContext.getUserPrincipal().getName();
            ClienteResponseDTO clienteLogado = clienteService.findByEmail(email);
            
            if (clienteLogado == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("Cliente não encontrado")
                    .build();
            }
            
            clienteService.deletar(clienteLogado.id());
            return Response.noContent().build();
            
        } catch (jakarta.ws.rs.NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(e.getMessage())
                .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Erro ao deletar conta: " + e.getMessage())
                .build();
        }
    }
}