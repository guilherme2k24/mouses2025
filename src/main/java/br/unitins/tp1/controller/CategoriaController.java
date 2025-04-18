package br.unitins.tp1.controller;

import br.unitins.tp1.dto.CategoriaRequestDTO;
import br.unitins.tp1.dto.CategoriaResponseDTO;
import br.unitins.tp1.service.CategoriaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/categorias")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoriaController {

    @Inject
    CategoriaService service;

    @POST
    public void salvar(CategoriaRequestDTO dto) {
        service.salvar(dto);
    }

    @GET
    public List<CategoriaResponseDTO> listarTodos() {
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    public CategoriaResponseDTO buscarPorId(@PathParam("id") Long id) {
        return service.buscarPorId(id);
    }

    @PUT
    @Path("/{id}")
    public void atualizar(@PathParam("id") Long id, CategoriaRequestDTO dto) {
        service.atualizar(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void deletar(@PathParam("id") Long id) {
        service.deletar(id);
    }
}
