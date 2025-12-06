package br.unitins.tp1.resource;

import org.jboss.logging.Logger;

import br.unitins.tp1.dto.AuthDTO;
import br.unitins.tp1.dto.AuthResponseDTO;
import br.unitins.tp1.dto.ClienteRequestDTO;
import br.unitins.tp1.dto.UsuarioResponseDTO;
import br.unitins.tp1.model.Cliente;
import br.unitins.tp1.model.Endereco;
import br.unitins.tp1.model.Perfil;
import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.repository.ClienteRepository;
import br.unitins.tp1.repository.UsuarioRepository;
import br.unitins.tp1.service.HashService;
import br.unitins.tp1.service.JwtService;
import br.unitins.tp1.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/auth")
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

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    ClienteRepository clienteRepository;

    @POST
    @Path("/login")
    public Response login(@Valid AuthDTO dto) {
        LOG.infof("Tentativa de login para usuário: %s", dto.username());

        String hash;
        try {
            hash = hashService.getHashSenha(dto.senha());
        } catch (Exception e) {
            LOG.error("Erro ao gerar hash da senha", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                         .entity("Erro interno no servidor")
                         .build();
        }

        UsuarioResponseDTO usuario = usuarioService.findByUsernameAndSenha(dto.username(), hash);

        if (usuario == null) {
            LOG.warnf("Falha de autenticação para usuário: %s", dto.username());
            return Response.status(Status.UNAUTHORIZED)
                         .entity("Credenciais inválidas")
                         .build();
        }

        String token = jwtService.generateJwt(usuario.username(), usuario.perfil().name());

        LOG.infof("Login bem-sucedido para usuário: %s, Perfil: %s", usuario.username(), usuario.perfil());

        AuthResponseDTO response = new AuthResponseDTO(usuario, token);
        return Response.ok(response).build();
    }

    @POST
    @Path("/registrar")
    @Transactional
    public Response registrarPublico(@Valid ClienteRequestDTO dto) {
        LOG.infof("Tentativa de registro público para: %s", dto.email());

        if (dto.perfil() == Perfil.ADM) {
            LOG.warnf("Tentativa de criar ADM via registro público: %s", dto.email());
            
        }


        if (usuarioRepository.findByUsername(dto.email()).isPresent()) {
            return Response.status(Status.CONFLICT)
                        .entity("Email já está cadastrado")
                        .build();
        }

        
        if (clienteRepository.findByCpf(dto.cpf()).isPresent()) {
            return Response.status(Status.CONFLICT)
                        .entity("CPF já cadastrado")
                        .build();
        }

        try {
            
            Cliente cliente = new Cliente();
            cliente.setNome(dto.nome());
            cliente.setEmail(dto.email());
            cliente.setTelefone(dto.telefone());
            cliente.setCpf(dto.cpf());
            cliente.setSenha(hashService.getHashSenha(dto.senha()));
            cliente.setPerfil(Perfil.USER); 

        
            if (dto.enderecos() != null && !dto.enderecos().isEmpty()) {
                dto.enderecos().forEach(enderecoDTO -> {
                    Endereco endereco = new Endereco();
                    endereco.setCep(enderecoDTO.cep());
                    endereco.setRua(enderecoDTO.rua());
                    endereco.setNumero(enderecoDTO.numero());
                    endereco.setComplemento(enderecoDTO.complemento());
                    endereco.setInformacoesAdicionais(enderecoDTO.informacoesAdicionais());
                    cliente.getEnderecos().add(endereco);
                });
            }

            clienteRepository.persist(cliente);

            
            Usuario usuario = new Usuario();
            usuario.setUsername(dto.email());
            usuario.setSenha(hashService.getHashSenha(dto.senha()));
            usuario.setPerfil(Perfil.USER); 
            usuario.setCliente(cliente);

            usuarioRepository.persist(usuario);

            
            String token = jwtService.generateJwt(usuario.getUsername(), usuario.getPerfil().name());
            UsuarioResponseDTO usuarioResponse = UsuarioResponseDTO.valueOf(usuario);

            AuthResponseDTO response = new AuthResponseDTO(usuarioResponse, token);

            LOG.infof("Registro público bem-sucedido para: %s", dto.email());
            return Response.status(Status.CREATED).entity(response).build();

        } catch (Exception e) {
            LOG.error("Erro durante o registro público", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                        .entity("Erro interno no servidor")
                        .build();
        }
    }

    @POST
    @Path("/admin/criar-usuario")
    @RolesAllowed("Adm") 
    @Transactional
    public Response criarUsuarioComoAdmin(@Valid ClienteRequestDTO dto) {
        LOG.infof("Admin criando usuário: %s", dto.email());

        
        if (usuarioRepository.findByUsername(dto.email()).isPresent()) {
            return Response.status(Status.CONFLICT)
                        .entity("Email já está cadastrado")
                        .build();
        }

        
        if (clienteRepository.findByCpf(dto.cpf()).isPresent()) {
            return Response.status(Status.CONFLICT)
                        .entity("CPF já cadastrado")
                        .build();
        }

        try {
            
            Cliente cliente = new Cliente();
            cliente.setNome(dto.nome());
            cliente.setEmail(dto.email());
            cliente.setTelefone(dto.telefone());
            cliente.setCpf(dto.cpf());
            cliente.setSenha(hashService.getHashSenha(dto.senha()));
            cliente.setPerfil(dto.perfil() != null ? dto.perfil() : Perfil.USER);

            
            if (dto.enderecos() != null && !dto.enderecos().isEmpty()) {
                dto.enderecos().forEach(enderecoDTO -> {
                    Endereco endereco = new Endereco();
                    endereco.setCep(enderecoDTO.cep());
                    endereco.setRua(enderecoDTO.rua());
                    endereco.setNumero(enderecoDTO.numero());
                    endereco.setComplemento(enderecoDTO.complemento());
                    endereco.setInformacoesAdicionais(enderecoDTO.informacoesAdicionais());
                    cliente.getEnderecos().add(endereco);
                });
            }

            clienteRepository.persist(cliente);

            
            Usuario usuario = new Usuario();
            usuario.setUsername(dto.email());
            usuario.setSenha(hashService.getHashSenha(dto.senha()));
            usuario.setPerfil(cliente.getPerfil());
            usuario.setCliente(cliente);

            usuarioRepository.persist(usuario);

            
            String token = jwtService.generateJwt(usuario.getUsername(), usuario.getPerfil().name());
            UsuarioResponseDTO usuarioResponse = UsuarioResponseDTO.valueOf(usuario);

            AuthResponseDTO response = new AuthResponseDTO(usuarioResponse, token);

            LOG.infof("Admin criou usuário: %s com perfil: %s", dto.email(), usuario.getPerfil());
            return Response.status(Status.CREATED).entity(response).build();

        } catch (Exception e) {
            LOG.error("Erro durante criação de usuário por admin", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                        .entity("Erro interno no servidor")
                        .build();
        }
    }
}