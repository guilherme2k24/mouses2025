package br.unitins.tp1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.tp1.dto.PedidoRequestDTO;
import br.unitins.tp1.dto.PedidoResponseDTO;
import br.unitins.tp1.service.PedidoService;
import io.quarkus.security.ForbiddenException;
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
    @RolesAllowed({"ADM", "USER"})
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
    @RolesAllowed({"ADM", "USER"})
    public Response listarTodos() {
        try {
            if (securityIdentity.hasRole("ADM")) {
                List<PedidoResponseDTO> lista = pedidoService.listarTodos();
                LOG.infof("Administrador listando todos os pedidos: %d encontrados", lista.size());
                return Response.ok(lista).build();
            } else {
                List<PedidoResponseDTO> lista = pedidoService.listarPorClienteLogado();
                LOG.infof("Usuário listando seus pedidos (%s): %d encontrados",
                        securityIdentity.getPrincipal().getName(), lista.size());
                return Response.ok(lista).build();
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
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorStatus(@PathParam("status") String status) {
        try {
            List<PedidoResponseDTO> pedidos;
            
            if (securityIdentity.hasRole("ADM")) {
                pedidos = pedidoService.buscarPorStatusParaAdmin(status);
            } else {
                pedidos = pedidoService.buscarPorStatusParaUsuarioLogado(status);
            }
            
            LOG.infof("Encontrados %d pedidos com status: %s", pedidos.size(), status);
            return Response.ok(pedidos).build();
        } catch (Exception e) {
            LOG.errorf("Erro ao buscar pedidos por status: %s", e.getMessage());
            return Response.status(Status.BAD_REQUEST)
                    .entity("Erro: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            PedidoResponseDTO dto = pedidoService.buscarPorId(id);
            
            if (dto == null) {
                LOG.warnf("Pedido com id %d não encontrado", id);
                return Response.status(Status.NOT_FOUND).build();
            }
            
            LOG.infof("Pedido encontrado: %d", id);
            return Response.ok(dto).build();
            
        } catch (ForbiddenException e) {
            LOG.warnf("Acesso negado ao pedido %d: %s", id, e.getMessage());
            return Response.status(Status.FORBIDDEN)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            LOG.errorf("Erro ao buscar pedido por id: %s", e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar pedido: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADM")
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
    @RolesAllowed("ADM")
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
    @RolesAllowed({"ADM", "USER"})
    public Response cancelar(@PathParam("id") Long id) {
        try {
        
            pedidoService.verificarPermissaoCancelamento(id);
            pedidoService.cancelarPedido(id);
            LOG.infof("Pedido %d cancelado com sucesso", id);
            return Response.ok("Pedido cancelado com sucesso").build();
        } catch (ForbiddenException e) {
            LOG.warnf("Acesso negado para cancelar pedido %d: %s", id, e.getMessage());
            return Response.status(Status.FORBIDDEN)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            LOG.errorf("Erro ao cancelar pedido %d: %s", id, e.getMessage());
            return Response.status(Status.BAD_REQUEST)
                    .entity("Erro ao cancelar pedido: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADM")
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