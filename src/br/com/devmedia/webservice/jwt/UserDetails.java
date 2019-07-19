package br.com.devmedia.webservice.jwt;

import java.security.Principal;
import java.util.List;

public class UserDetails implements Principal {

	private final String username;
    private final List<String> roles;
    
    public UserDetails(String username, List<String> roles) {
        this.username = username;
        this.roles = roles;
    }
    
    @Override
    public String getName() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }

}
