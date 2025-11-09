package br.unitins.tp1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.tp1.dto.PedidoRequestDTO;
import br.unitins.tp1.dto.PedidoResponseDTO;
import br.unitins.tp1.service.PedidoService;
import io.quarkus.security.identity.SecurityIdentity;
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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/pedidos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    PedidoService pedidoService;

    @Inject
    SecurityIdentity securityIdentity;

    private static final Logger LOG = Logger.getLogger(PedidoResource.class);

    @POST
    @RolesAllowed("Adm")
    public Response salvar(@Valid PedidoRequestDTO dto) {
        try {
            PedidoResponseDTO responseDTO = pedidoService.criar(dto);
            LOG.infof("Pedido criado com sucesso: %d com %d itens, valor total: R$ %.2f", 
                    responseDTO.getId(), 
                    responseDTO.getItens().size(),
                    responseDTO.getValorTotal());
            return Response.status(Status.CREATED).entity(responseDTO).build();
        } catch (Exception e) {
            LOG.errorf("Erro ao criar pedido: %s", e.getMessage());
            return Response.status(Status.BAD_REQUEST)
                    .entity("Erro ao criar pedido: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @RolesAllowed({"Adm", "User"})
    public Response listarTodos() {
        try {
            if (securityIdentity != null && securityIdentity.getPrincipal() != null && securityIdentity.hasRole("Adm")) {
                List<PedidoResponseDTO> lista = pedidoService.listarTodos();
                LOG.infof("Listando todos os pedidos (Adm): %d encontrados", lista.size());
                return Response.ok(lista).build();
            } else if (securityIdentity != null && securityIdentity.getPrincipal() != null) {
                List<PedidoResponseDTO> lista = pedidoService.listarPorClienteLogado();
                LOG.infof("Listando pedidos do cliente logado (%s): %d encontrados",
                        securityIdentity.getPrincipal().getName(), lista.size());
                return Response.ok(lista).build();
            } else {
                // No authentication - return empty list or require authentication
                LOG.warn("Tentativa de acessar pedidos sem autenticação");
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Autenticação necessária para acessar pedidos")
                        .build();
            }
        } catch (Exception e) {
            LOG.errorf("Erro ao listar pedidos: %s", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar pedidos: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/status/{status}")
    @RolesAllowed({"Adm", "User"})
    public Response buscarPorStatus(@PathParam("status") String status) {
        try {
            List<PedidoResponseDTO> pedidos = pedidoService.buscarPorStatus(status);
            LOG.infof("Encontrados %d pedidos com status: %s", pedidos.size(), status);
            return Response.ok(pedidos).build();
        } catch (Exception e) {
            LOG.errorf("Erro ao buscar pedidos por status: %s", e.getMessage());
            return Response.status(Status.BAD_REQUEST)
                    .entity("Status inválido: " + status)
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response buscarPorId(@PathParam("id") Long id) {
        PedidoResponseDTO dto = pedidoService.buscarPorId(id);
        if (dto == null) {
            LOG.warnf("Pedido com id %d não encontrado", id);
            return Response.status(Status.NOT_FOUND).build();
        }
        LOG.infof("Pedido encontrado: %d", id);
        return Response.ok(dto).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response atualizar(@PathParam("id") Long id, @Valid PedidoRequestDTO dto) {
        try {
            pedidoService.atualizar(id, dto);
            LOG.infof("Pedido com id %d atualizado com sucesso (itens: %d)", 
                    id, 
                    dto.getItens() != null ? dto.getItens().size() : 0);
            return Response.noContent().build();
        } catch (Exception e) {
            LOG.errorf("Erro ao atualizar pedido %d: %s", id, e.getMessage());
            return Response.status(Status.BAD_REQUEST)
                    .entity("Erro ao atualizar pedido: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}/status")
    @RolesAllowed("Adm")
    public Response atualizarStatus(@PathParam("id") Long id, @QueryParam("status") String status) {
        try {
            pedidoService.atualizarStatus(id, status);
            LOG.infof("Status do pedido %d atualizado para: %s", id, status);
            return Response.noContent().build();
        } catch (Exception e) {
            LOG.errorf("Erro ao atualizar status do pedido %d: %s", id, e.getMessage());
            return Response.status(Status.BAD_REQUEST)
                    .entity("Erro ao atualizar status: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}/cancelar")
    @RolesAllowed({"Adm", "User"})
    public Response cancelar(@PathParam("id") Long id) {
        try {
            pedidoService.cancelarPedido(id);
            LOG.infof("Pedido %d cancelado com sucesso", id);
            return Response.ok("Pedido cancelado com sucesso").build();
        } catch (Exception e) {
            LOG.errorf("Erro ao cancelar pedido %d: %s", id, e.getMessage());
            return Response.status(Status.BAD_REQUEST)
                    .entity("Erro ao cancelar pedido: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response deletar(@PathParam("id") Long id) {
        try {
            pedidoService.deletar(id);
            LOG.infof("Pedido com id %d deletado com sucesso", id);
            return Response.noContent().build();
        } catch (Exception e) {
            LOG.errorf("Erro ao deletar pedido %d: %s", id, e.getMessage());
            return Response.status(Status.BAD_REQUEST)
                    .entity("Erro ao deletar pedido: " + e.getMessage())
                    .build();
        }
    }
}
