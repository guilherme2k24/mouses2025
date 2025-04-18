package br.unitins.tp1.controller;

import java.util.List;

import br.unitins.tp1.dto.MarcaRequestDTO;
import br.unitins.tp1.dto.MarcaResponseDTO;
import br.unitins.tp1.service.MarcaService;
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

@Path("/marcas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarcaController {


    @Inject
    MarcaService service;

    @POST
    public void salvar(MarcaRequestDTO dto) {
        service.salvar(dto);
    }

    @GET
    public List<MarcaResponseDTO> listar() {
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    public MarcaResponseDTO buscarPorID(@PathParam("id") Long id) {
        return service.buscarPorId(id);
    }

    @PUT
    @Path("/{id}")
    public void atualizar(@PathParam("id") Long id, MarcaRequestDTO dto) {
        service.atualizar(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void deletar(@PathParam("id") Long id) {
        service.deletar(id);
    }



}
