package br.com.devmedia.webservice.resources;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.devmedia.webservice.jwt.JWTSecurityContext;
import br.com.devmedia.webservice.jwt.TokenJWTUtil;
import br.com.devmedia.webservice.jwt.UserDetails;
import br.com.devmedia.webservice.model.domain.Usuario;
import br.com.devmedia.webservice.service.UsuarioService;
import br.com.devmedia.webservice.service.exception.UnauthenticatedException;

@Path("/login")
public class LoginJWTResource {

	@POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response autenticarUsuario(@FormParam("login") String login,
                                      @FormParam("password") String password) {
        Usuario usuario = validarCredenciais(login, password);
        String token = TokenJWTUtil.gerarToken(usuario.getUsername(), usuario.recuperarRoles());

        return Response.ok().header("Authorization", "Bearer " + token).build();
    }
	
	/*
	 keytool -genkey -alias server -keyalg RSA -keysize 2048 -keystore keystore.jks
	 
	 keytool -importkeystore -srckeystore keystore.jks -destkeystore keystore.jks -deststoretype pkcs12
	 */
	
	@POST
    @Path("refresh")
    @PermitAll
    public Response atualizarToken(@Context ContainerRequestContext requestContext) {
        JWTSecurityContext JWTSecurityContext = (JWTSecurityContext) requestContext.getSecurityContext();
        UserDetails userDetails = (UserDetails) JWTSecurityContext.getUserPrincipal();
        String token = TokenJWTUtil.gerarToken(userDetails.getName(), userDetails.getRoles());

        return Response.ok().header("Authorization", "Bearer " + token).build();
    }
	
	private Usuario validarCredenciais(String login, String password) {
        UsuarioService usuarioService = new UsuarioService();

        Usuario usuario = usuarioService.recuperarUsuarioComLoginESenha(login, password);
        if (usuario == null)
            throw new UnauthenticatedException("Usuário não autenticado: nome do usuário ou senha inválidos!");

        return usuario;
    }
}
