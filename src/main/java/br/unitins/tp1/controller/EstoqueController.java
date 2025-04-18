package br.unitins.tp1.controller;

import java.util.List;

import br.unitins.tp1.dto.EstoqueRequestDTO;
import br.unitins.tp1.dto.EstoqueResponseDTO;
import br.unitins.tp1.service.EstoqueService;
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

@Path("/estoques")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EstoqueController {

    @Inject
    EstoqueService service;

    @POST
    public void salvar(EstoqueRequestDTO dto) {
        service.criarEstoque(dto);
    }

    @GET
    public List<EstoqueResponseDTO> listar() {
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    public EstoqueResponseDTO buscarPorID(@PathParam("id") Long id) {
        return service.buscarPorId(id);
    } 

    @PUT
    @Path("{id}")
    public void atualizar(@PathParam("id") Long id, EstoqueRequestDTO dto) {
        service.atualizar(id, dto);
    }

    @DELETE
    @Path("{id}")
    public void deletar(@PathParam("id") Long id) {
        service.deletar(id);
    }
}
