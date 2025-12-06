package br.unitins.tp1.resource;

import java.util.List;

import br.unitins.tp1.dto.FornecedorRequestDTO;
import br.unitins.tp1.dto.FornecedorResponseDTO;
import br.unitins.tp1.service.FornecedorService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/fornecedores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FornecedorResource {

    @Inject
    FornecedorService fornecedorService;

    @GET
    @RolesAllowed("ADM")
    public List<FornecedorResponseDTO> findAll() {
        return fornecedorService.findAll();
    }

    @GET
    @RolesAllowed("ADM")
    @Path("/{id}")
    public FornecedorResponseDTO findById(@PathParam("id") Long id) {
        return fornecedorService.findById(id);
    }

    @GET
    @RolesAllowed("ADM")
    @Path("/cnpj/{cnpj}")
    public FornecedorResponseDTO findByCnpj(@PathParam("cnpj") String cnpj) {
        return fornecedorService.findByCnpj(cnpj);
    }

    @POST
    @RolesAllowed({"ADM", "USER"})
    public Response create(@Valid FornecedorRequestDTO dto) {
        FornecedorResponseDTO fornecedor = fornecedorService.create(dto);
        return Response.status(Response.Status.CREATED).entity(fornecedor).build();
    }

    @PUT
    @RolesAllowed({"ADM", "USER"})
    @Path("/{id}")
    public FornecedorResponseDTO update(@PathParam("id") Long id, @Valid FornecedorRequestDTO dto) {
        return fornecedorService.update(id, dto);
    }

    @DELETE
    @RolesAllowed({"ADM", "USER"})
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        fornecedorService.delete(id);
        return Response.noContent().build();
    }
}
