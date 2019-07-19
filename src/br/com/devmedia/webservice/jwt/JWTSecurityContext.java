package br.com.devmedia.webservice.jwt;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

public class JWTSecurityContext implements SecurityContext {

	private UserDetails userDetails;
    private final boolean secure;
    
    public JWTSecurityContext(UserDetails userDetails, boolean secure) {
        this.userDetails = userDetails;
        this.secure = secure;
    }
    
    @Override
    public Principal getUserPrincipal() {
        return this.userDetails;
    }

    @Override
    public boolean isUserInRole(String role) {
        return this.userDetails.getRoles().contains(role);
    }

    @Override
    public boolean isSecure() {
        return secure;
    }

    @Override
    public String getAuthenticationScheme() {
        return "Bearer";
    }

}
