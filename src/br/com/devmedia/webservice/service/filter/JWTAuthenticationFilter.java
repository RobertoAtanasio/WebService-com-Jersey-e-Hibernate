package br.com.devmedia.webservice.service.filter;

import java.io.IOException;
import java.security.Key;
import java.util.List;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import br.com.devmedia.webservice.jwt.JWTSecurityContext;
import br.com.devmedia.webservice.jwt.KeyGenerator;
import br.com.devmedia.webservice.jwt.TokenJWTUtil;
import br.com.devmedia.webservice.jwt.UserDetails;
import br.com.devmedia.webservice.resources.LoginJWTResource;
import br.com.devmedia.webservice.service.exception.UnauthenticatedException;

// o valor Priorities.AUTHENTICATION indica que este filtro deve ser executado antes de todos os outros

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JWTAuthenticationFilter implements ContainerRequestFilter {

	private KeyGenerator keyGenerator = new KeyGenerator();

    @Context
    private UriInfo uriInfo;
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
    	
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if(authorizationHeader != null && authorizationHeader.contains("Bearer ")) {
            String token = authorizationHeader.substring("Bearer".length()).trim();

            Key key = keyGenerator.generateKey();

            if (TokenJWTUtil.tokenValido(token, key)) {
                String nome = TokenJWTUtil.recuperarNome(token, key);
                List<String> regras = TokenJWTUtil.recuperarRoles(token, key);
                UserDetails userDetails = new UserDetails(nome, regras);

                boolean secure = requestContext.getSecurityContext().isSecure();
                requestContext.setSecurityContext(new JWTSecurityContext(userDetails, secure));
                return;
            }
        } else if (acessoParaLoginNaAPI(requestContext)) {
            return;
        } else if (acessoParaMetodosDeConsulta(requestContext)) {
            return;
        }
        throw new UnauthenticatedException("Token inválido/expirado ou usuário não autenticado!");
    }
	
	private boolean acessoParaLoginNaAPI(ContainerRequestContext requestContext) {
		// Estes comnados formatam as URI de acordo com os parâmetros passados. Com isso, podemos tratar as URI's que serão controladas pelo Token
//		System.out.println("<<<< acessoParaLoginNaAPI 1: " + requestContext.getUriInfo().getAbsolutePath().toString() );
//		System.out.println("<<<< acessoParaLoginNaAPI 2: " + uriInfo.getBaseUriBuilder().path(LoginJWTResource.class).build().toString() );
//		System.out.println("<<<< acessoParaLoginNaAPI 3: " + uriInfo.getBaseUriBuilder().path(MarcaResource.class).build().toString() );
//		System.out.println("<<<< acessoParaLoginNaAPI 4: " + requestContext.getMethod() );
        return requestContext.getUriInfo().getAbsolutePath().toString()
                .equals(uriInfo.getBaseUriBuilder().path(LoginJWTResource.class).build().toString());
    }

    private boolean acessoParaMetodosDeConsulta(ContainerRequestContext requestContext) {
//    	System.out.println("<<<< acessoParaMetodosDeConsulta: " + requestContext.getMethod() );
        return "GET".equalsIgnoreCase(requestContext.getMethod());
    }

}
