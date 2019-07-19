package br.com.devmedia.webservice;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Main {

	public static void main(String args[]) {
//        UsuarioDAO dao = new UsuarioDAO();
//
//        EntityManager em = JPAUtil.getEntityManager();
//        em.getTransaction().begin();
//        dao.salvarUsuario(new Usuario("Eduardo", "eduardo", "123456"));
//        em.getTransaction().commit();
//        em.close();
		

		Instant agora = Instant.now();
		System.out.println(agora.toString());
		System.out.println(Date.from(LocalDateTime.now().plusMinutes(60L).atZone(ZoneId.systemDefault()).toInstant()));
		System.out.println(LocalDateTime.now().plusMinutes(60L).atZone(ZoneId.systemDefault()).toInstant());
		
		
		
    }
}
