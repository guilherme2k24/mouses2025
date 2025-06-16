package br.unitins.tp1.resource;

import org.jboss.logging.Logger;

import br.unitins.tp1.dto.AuthDTO;
import br.unitins.tp1.dto.UsuarioResponseDTO;
import br.unitins.tp1.service.HashService;
import br.unitins.tp1.service.JwtService;
import br.unitins.tp1.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    private static final Logger LOG = Logger.getLogger(AuthResource.class);

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;

    @Inject
    UsuarioService usuarioService;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(AuthDTO dto) {
        LOG.infof("Tentativa de login para usuário: %s", dto.username());

        String hash;
        try {
            hash = hashService.getHashSenha(dto.senha());
        } catch (Exception e) {
            LOG.error("Erro ao gerar hash da senha", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        UsuarioResponseDTO usuario = usuarioService.findByUsernameAndSenha(dto.username(), hash);

        if (usuario == null) {
            LOG.warnf("Falha de autenticação para usuário: %s", dto.username());
            return Response.status(Status.UNAUTHORIZED).build();
        }

        String token = jwtService.generateJwt(usuario.username(), usuario.perfil().getNome());

        LOG.infof("Login bem-sucedido para usuário: %s", usuario.username());

        return Response.ok().header("Authorization", token).build();
    }
}
