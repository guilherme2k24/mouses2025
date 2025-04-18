package br.unitins.tp1.controller;

import java.util.List;

import br.unitins.tp1.dto.MousesRequestDTO;
import br.unitins.tp1.dto.MousesResponseDTO;
import br.unitins.tp1.service.MousesServices;
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

@Path("/mouses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MousesController {

    @Inject
    MousesServices services;

     @POST
    public void salvar(MousesRequestDTO dto) {
        services.salvar(dto);
    }
      @GET
        public List<MousesResponseDTO> listar() {
            return services.listarTodos();
        }
    
      @GET
    @Path("/{id}")
    public MousesResponseDTO buscarPorId(@PathParam("id") Long id) {
        return services.buscarPorID(id);
    }

    @PUT
    @Path("/{id}")
    public void atualizar(@PathParam("id") Long id, MousesRequestDTO dto) {
        services.atualizar(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void deletar(@PathParam("id") Long id) {
        services.deletar(id);
    }
}
