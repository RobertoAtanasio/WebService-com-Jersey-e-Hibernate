package br.com.devmedia.webservice.service.filter;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Priority;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import br.com.devmedia.webservice.service.exception.UnauthorizedException;

// O valor Priorities.AUTHORIZATION indica que este filtro deve ser executado após o filtro AUTHENTICATION
// Apenas as reqisições passadas pelo filtro de autorização serão analisadas por este novo filtro.

@Provider
@Priority(Priorities.AUTHORIZATION)
public class JWTAuthorizationFilter implements ContainerRequestFilter{

	@Context
    private ResourceInfo resourceInfo;
	
	@Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        try {
        	// O contexto de segurança já possi as informações do usuário e das roles a ele associadass.
            SecurityContext securityContext = requestContext.getSecurityContext();
            if (verificarPermissoesDoMetodo(securityContext)) {
                return;
            } else if (verificarPermissoesDaClasse(securityContext)) {
                return;
            }
        } catch (Exception ex) {
            String usuario = requestContext.getSecurityContext().getUserPrincipal().getName();
            throw new UnauthorizedException("Usuário " + usuario + " não tem " +
                    "autorização para executar essa funcionalidade.");
        }
    }

	private boolean verificarPermissoesDoMetodo(SecurityContext securityContext) throws Exception {
		// Obter método responsável por atender a requisição.
        Method metodoDoRecurso = resourceInfo.getResourceMethod();

        if (metodoDoRecurso.isAnnotationPresent(PermitAll.class)) {
            return true;
        }

        List<String> permissoesDoMetodo = recuperarPermissoes(metodoDoRecurso);

        if(permissoesDoMetodo != null) {
            verificarPermissoes(permissoesDoMetodo, securityContext);
            return true;
        }

        return false;
    }

    private boolean verificarPermissoesDaClasse(SecurityContext securityContext) throws Exception {
        Class<?> classeDoRecurso = resourceInfo.getResourceClass();

        if (classeDoRecurso.isAnnotationPresent(PermitAll.class)) {
            return true;
        }

        List<String> permissoesDaClasse = recuperarPermissoes(classeDoRecurso);

        if(permissoesDaClasse != null) {
            verificarPermissoes(permissoesDaClasse, securityContext);
            return true;
        }

        return false;
    }

    private void verificarPermissoes(List<String> permissoes, SecurityContext securityContext) throws Exception {
        for (final String role : permissoes) {
            if (securityContext.isUserInRole(role)) {
                return;
            }
        }
        throw new Exception();
    }

    private List<String> recuperarPermissoes(AnnotatedElement elementoAnotado) {
        if (elementoAnotado.isAnnotationPresent(RolesAllowed.class)) {
            RolesAllowed regrasPermitidas = elementoAnotado.getAnnotation(RolesAllowed.class);
            if (regrasPermitidas == null) {
                return new ArrayList<String>();
            } else {
                String[] permissoes = regrasPermitidas.value();
                return Arrays.asList(permissoes);
            }
        }
        return null;
    }
}
