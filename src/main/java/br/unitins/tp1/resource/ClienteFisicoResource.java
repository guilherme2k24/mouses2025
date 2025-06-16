package br.unitins.tp1.resource;

import java.security.Principal;
import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.tp1.dto.ClienteFisicoRequestDTO;
import br.unitins.tp1.dto.ClienteFisicoResponseDTO;
import br.unitins.tp1.service.ClienteFisicoService;
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
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/clientes/fisicos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteFisicoResource {

    private static final Logger LOG = Logger.getLogger(ClienteFisicoResource.class);

    @Inject
    ClienteFisicoService service;

    @Context
    SecurityContext securityContext;

    @POST
    @RolesAllowed("Adm")
    public Response salvar(@Valid ClienteFisicoRequestDTO dto) {
        LOG.infof("Solicitação para salvar novo cliente físico: %s", dto.getNome());
        ClienteFisicoResponseDTO responseDTO = service.salvar(dto);
        LOG.infof("Cliente físico criado com ID: %d", responseDTO.getId());
        return Response.status(Response.Status.CREATED).entity(responseDTO).build();
    }

    @GET
    @RolesAllowed("Adm")
    public List<ClienteFisicoResponseDTO> listar() {
        LOG.debug("Listando todos os clientes físicos.");
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Adm", "ClienteFisico"})
    public Response buscarPorId(@PathParam("id") Long id) {
        Principal user = securityContext.getUserPrincipal();
        LOG.debugf("Usuário %s solicitou cliente físico com ID %d", user.getName(), id);

        if (securityContext.isUserInRole("Adm")) {
            ClienteFisicoResponseDTO response = service.buscarPorId(id);
            LOG.debug("Acesso permitido para perfil Adm.");
            return Response.ok(response).build();
        } else if (securityContext.isUserInRole("ClienteFisico")) {
            Long idUsuarioLogado = getIdUsuarioLogado(user);
            if (!idUsuarioLogado.equals(id)) {
                LOG.warnf("Usuário %s tentou acessar cliente físico ID %d sem permissão.", user.getName(), id);
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Você não tem permissão para acessar este recurso.")
                        .build();
            }
            ClienteFisicoResponseDTO response = service.buscarPorId(id);
            LOG.debug("Acesso permitido para cliente físico ao seu próprio recurso.");
            return Response.ok(response).build();
        } else {
            LOG.warnf("Perfil não autorizado para usuário %s.", user.getName());
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Perfil não autorizado.")
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response atualizar(@PathParam("id") Long id, @Valid ClienteFisicoRequestDTO dto) {
        LOG.infof("Solicitação para atualizar cliente físico ID %d", id);
        try {
            service.atualizar(id, dto);
            LOG.infof("Cliente físico ID %d atualizado com sucesso.", id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warnf("Cliente físico com ID %d não encontrado para atualização.", id);
            return Response.status(Response.Status.NOT_FOUND).entity("Cliente físico não encontrado para atualização").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response deletar(@PathParam("id") Long id) {
        LOG.infof("Solicitação para deletar cliente físico ID: %d", id);
        try {
            service.deletar(id);
            LOG.infof("Cliente físico ID %d deletado com sucesso.", id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            LOG.warnf("Cliente físico com ID %d não encontrado para exclusão.", id);
            return Response.status(Response.Status.NOT_FOUND).entity("Cliente físico não encontrado para exclusão").build();
        }
    }

    private Long getIdUsuarioLogado(Principal user) {
        return Long.valueOf(user.getName());
    }
}
