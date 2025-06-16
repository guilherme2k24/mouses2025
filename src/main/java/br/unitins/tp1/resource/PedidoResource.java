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
        PedidoResponseDTO responseDTO = pedidoService.criar(dto);
        LOG.infof("Pedido criado com sucesso: %d", responseDTO.getId());
        return Response.status(Status.CREATED).entity(responseDTO).build();
    }

    @GET
    @RolesAllowed({"Adm", "User"})
    public List<PedidoResponseDTO> listarTodos() {
        if (securityIdentity.hasRole("Adm")) {
            List<PedidoResponseDTO> lista = pedidoService.listarTodos();
            LOG.infof("Listando todos os pedidos (Adm): %d encontrados", lista.size());
            return lista;
        } else {
            List<PedidoResponseDTO> lista = pedidoService.listarPorClienteLogado();
            LOG.infof("Listando pedidos do cliente logado (%s): %d encontrados",
                    securityIdentity.getPrincipal().getName(), lista.size());
            return lista;
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
        pedidoService.atualizar(id, dto);
        LOG.infof("Pedido com id %d atualizado com sucesso", id);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response deletar(@PathParam("id") Long id) {
        pedidoService.deletar(id);
        LOG.infof("Pedido com id %d deletado com sucesso", id);
        return Response.noContent().build();
    }
}
