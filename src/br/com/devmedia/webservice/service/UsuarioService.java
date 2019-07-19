package br.com.devmedia.webservice.service;

import br.com.devmedia.webservice.model.dao.UsuarioDAO;
import br.com.devmedia.webservice.model.domain.Usuario;

public class UsuarioService {

	private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario recuperarUsuarioComLoginESenha(String usuario, String password) {
    	System.out.println(">>> recuperarUsuarioComLoginESenha");
        return usuarioDAO.recuperarUsuarioPorUsernameEPassword(usuario, password);
    }
}
