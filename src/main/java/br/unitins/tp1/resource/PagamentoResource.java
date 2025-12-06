package br.unitins.tp1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.tp1.dto.PagamentoRequestDTO;
import br.unitins.tp1.dto.PagamentoResponseDTO;
import br.unitins.tp1.service.PagamentoService;
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

@Path("/pagamentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PagamentoResource {

    @Inject
    PagamentoService pagamentoService;

    private static final Logger LOG = Logger.getLogger(PagamentoResource.class);

    @POST
    @RolesAllowed("ADM")
    public Response salvar(@Valid PagamentoRequestDTO dto) {
        PagamentoResponseDTO responseDTO = pagamentoService.salvar(dto);
        LOG.infof("Pagamento cadastrado com sucesso: %d", responseDTO.getId());
        return Response.status(Response.Status.CREATED)
                       .entity(responseDTO)
                       .build();
    }

    @GET
    @RolesAllowed("ADM")
    public List<PagamentoResponseDTO> listarTodos() {
        List<PagamentoResponseDTO> lista = pagamentoService.listarTodos();
        LOG.infof("Total de pagamentos listados: %d", lista.size());
        return lista;
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            PagamentoResponseDTO dto = pagamentoService.buscarPorId(id);
            LOG.infof("Pagamento encontrado: %d", id);
            return Response.ok(dto).build();
        } catch (NotFoundException e) {
            LOG.warnf("Pagamento com id %d não encontrado", id);
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Pagamento não encontrado")
                           .build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response atualizar(@PathParam("id") Long id, @Valid PagamentoRequestDTO dto) {
        try {
            pagamentoService.atualizar(id, dto);
            LOG.infof("Pagamento com id %d atualizado com sucesso", id);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            LOG.warnf("Pagamento com id %d não encontrado para atualização", id);
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Pagamento não encontrado para atualização")
                           .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response deletar(@PathParam("id") Long id) {
        try {
            pagamentoService.deletar(id);
            LOG.infof("Pagamento com id %d deletado com sucesso", id);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            LOG.warnf("Pagamento com id %d não encontrado para exclusão", id);
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Pagamento não encontrado para exclusão")
                           .build();
        }
    }
}
