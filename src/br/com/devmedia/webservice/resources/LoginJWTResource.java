package br.com.devmedia.webservice.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.devmedia.webservice.jwt.TokenJWTUtil;
import br.com.devmedia.webservice.model.domain.Usuario;
import br.com.devmedia.webservice.service.UsuarioService;
import br.com.devmedia.webservice.service.exception.UnauthenticatedException;

@Path("/login")
public class LoginJWTResource {

	@GET
    @Consumes(MediaType.APPLICATION_JSON)
    public void teste() {
    	System.out.println(">>> entrou em teste");
    }

	@POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response autenticarUsuario(@FormParam("login") String login,
                                      @FormParam("password") String password) {
    	System.out.println(">>> entrou em autenticarUsuario");
        Usuario usuario = validarCredenciais(login, password);
        String token = TokenJWTUtil.gerarToken(usuario.getUsername(), usuario.recuperarRoles());

        return Response.ok().header("Authorization", "Bearer " + token).build();
    }
	
	private Usuario validarCredenciais(String login, String password) {
		System.out.println(">>> validarCredenciais");
        UsuarioService usuarioService = new UsuarioService();

        Usuario usuario = usuarioService.recuperarUsuarioComLoginESenha(login, password);
        if (usuario == null)
            throw new UnauthenticatedException("Usuário não autenticado: nome do usuário ou senha inválidos!");

        System.out.println(">>> Usuário: " + usuario.getNome());
        return usuario;
    }
}
