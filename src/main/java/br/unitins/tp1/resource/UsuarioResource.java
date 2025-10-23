package br.unitins.tp1.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.tp1.dto.UsuarioResponseDTO;
import br.unitins.tp1.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
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
}
