package br.unitins.tp1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.tp1.dto.ItemDoPedidoRequestDTO;
import br.unitins.tp1.dto.ItemDoPedidoResponseDTO;
import br.unitins.tp1.service.ItemDoPedidoService;
import io.quarkus.security.identity.SecurityIdentity;
// import jakarta.annotation.security.RolesAllowed;
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
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;


@Deprecated
@Path("/itenspedido")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ItemDoPedidoResource {

    private static final Logger LOG = Logger.getLogger(ItemDoPedidoResource.class);

    @Inject
    ItemDoPedidoService service;

    @Inject
    SecurityIdentity securityIdentity;

    private Long getClienteId() {
        try {
            return Long.parseLong(securityIdentity.getPrincipal().getName());
        } catch (NumberFormatException e) {
            LOG.error("ID do cliente inválido no token", e);
            throw new WebApplicationException("ID do cliente inválido no token", Status.UNAUTHORIZED);
        }
    }

    @POST
    // @RolesAllowed("Adm")
    @Deprecated
    public Response salvar(@Valid ItemDoPedidoRequestDTO dto) {
        LOG.warnf("DEPRECATED: Endpoint /itenspedido POST está obsoleto. Use /pedidos para gerenciar itens.");
        return Response.status(Status.GONE)
                .entity("Este endpoint está obsoleto. Use POST /pedidos com a lista de itens incluída.")
                .build();
    }

    @GET
    // @RolesAllowed({"Adm", "User"})
    public List<ItemDoPedidoResponseDTO> listarTodos() {
        if (securityIdentity.hasRole("Adm")) {
            LOG.debug("Listando todos os itens do pedido para Adm");
            return service.listarTodos();
        } else {
            Long clienteId = getClienteId();
            LOG.debugf("Listando itens do pedido para cliente ID %d", clienteId);
            return service.listarPorClienteId(clienteId);
        }
    }

    @GET
    @Path("/{id}")
    // @RolesAllowed({"Adm", "User"})
    public Response buscarPorId(@PathParam("id") Long id) {
        if (securityIdentity.hasRole("Adm")) {
            LOG.debugf("Buscando item do pedido ID %d para Adm", id);
            ItemDoPedidoResponseDTO dto = service.buscarPorId(id);
            if (dto == null) {
                LOG.warnf("Item do pedido ID %d não encontrado", id);
                return Response.status(Status.NOT_FOUND).build();
            }
            return Response.ok(dto).build();
        } else {
            Long clienteId = getClienteId();
            LOG.debugf("Buscando item do pedido ID %d para cliente ID %d", id, clienteId);
            ItemDoPedidoResponseDTO dto = service.buscarPorIdSePertencerAoCliente(id, clienteId);
            if (dto == null) {
                LOG.warnf("Cliente ID %d tentou acessar item do pedido ID %d sem permissão", clienteId, id);
                return Response.status(Status.FORBIDDEN)
                        .entity("Você não tem permissão para acessar este item do pedido.")
                        .build();
            }
            return Response.ok(dto).build();
        }
    }

    @PUT
    @Path("/{id}")
    // @RolesAllowed("Adm")
    @Deprecated
    public Response atualizar(@PathParam("id") Long id, @Valid ItemDoPedidoRequestDTO dto) {
        LOG.warnf("DEPRECATED: Endpoint /itenspedido/{id} PUT está obsoleto. Use /pedidos para gerenciar itens.");
        return Response.status(Status.GONE)
                .entity("Este endpoint está obsoleto. Use PUT /pedidos/{id} para atualizar o pedido completo.")
                .build();
    }

    @DELETE
    @Path("/{id}")
    // @RolesAllowed("Adm")
    @Deprecated
    public Response deletar(@PathParam("id") Long id) {
        LOG.warnf("DEPRECATED: Endpoint /itenspedido/{id} DELETE está obsoleto. Use /pedidos para gerenciar itens.");
        return Response.status(Status.GONE)
                .entity("Este endpoint está obsoleto. Use PUT /pedidos/{id} para remover itens do pedido.")
                .build();
    }
}
