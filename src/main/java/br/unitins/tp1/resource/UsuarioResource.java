package br.unitins.tp1.resource;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.tp1.dto.ChangePasswordRequestDTO;
import br.unitins.tp1.dto.UsuarioResponseDTO;
import br.unitins.tp1.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioService usuarioService;

    private static final Logger LOG = Logger.getLogger(UsuarioResource.class);

    @GET
    @Path("/perfil")
    @RolesAllowed({ "Adm", "User" })
    public Response buscarUsuarioLogado() {
        String username = jwt.getSubject();
        LOG.infof("Buscando perfil do usuário logado: %s", username);

        UsuarioResponseDTO usuario = usuarioService.findByUsername(username);

        if (usuario == null) {
            LOG.warnf("Usuário não encontrado: %s", username);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        LOG.infof("Perfil encontrado para usuário: %s", username);
        return Response.ok(usuario).build();
    }
    
    @PUT
    @Path("/trocar-senha")
    @RolesAllowed({ "ADM", "USER" })
    public Response trocarSenha(@Valid ChangePasswordRequestDTO request) {
        try {
            String username = jwt.getSubject();
            LOG.infof("Tentativa de troca de senha para usuário: %s", username);
            
            usuarioService.trocarSenha(username, request);
            
            LOG.infof("Senha alterada com sucesso para usuário: %s", username);
            
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Senha alterada com sucesso");
            
            return Response.ok(response).build();
                
        } catch (Exception e) {
            LOG.error("Erro ao trocar senha: " + e.getMessage(), e);
            
            
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse)
                    .build();
        }
    }
}
