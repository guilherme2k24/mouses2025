package br.unitins.tp1.controller;

import java.util.List;

import br.unitins.tp1.dto.PagamentoRequestDTO;
import br.unitins.tp1.dto.PagamentoResponseDTO;
import br.unitins.tp1.service.PagamentoService;
import jakarta.inject.Inject;
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

@Path("/pagamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PagamentoController {

    @Inject
    PagamentoService pagamentoService;

    @GET
    public List<PagamentoResponseDTO> listarTodos() {
        return pagamentoService.listarTodos();
    }

    @GET
    @Path("/{id}")
    public PagamentoResponseDTO buscarPorId(@PathParam("id") Long id) {
        return pagamentoService.buscarPorId(id);
    }

    @POST
    public Response salvar(PagamentoRequestDTO dto) {
        pagamentoService.salvar(dto);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, PagamentoRequestDTO dto) {
        pagamentoService.atualizar(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        pagamentoService.deletar(id);
        return Response.noContent().build();
    }
}
