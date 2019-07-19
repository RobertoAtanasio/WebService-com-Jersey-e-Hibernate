package br.com.devmedia.webservice.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.devmedia.webservice.model.domain.Usuario;

public class UsuarioDAO {

	public Usuario recuperarUsuarioPorUsernameEPassword(String username, String password) {
		System.out.println(">>> recuperarUsuarioPorUsernameEPassword");
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.username = :username AND u.password = :password", Usuario.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public Usuario salvarUsuario(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return usuario;
    }
}
