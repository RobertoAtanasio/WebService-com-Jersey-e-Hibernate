package br.com.devmedia.webservice.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.devmedia.webservice.exceptions.DAOException;
import br.com.devmedia.webservice.exceptions.ErrorCode;

public class JPAUtil {

	private static EntityManagerFactory emf;
	
	// O objeto EntityManager:
	// 1. Ler o xml
	// 2. Estabelece a conexão com o BD
	// 3. Identifica as entidades.
	public static EntityManager getEntityManager() {
		try {		
			if (emf == null) {
				// nome "produtos" definido em persistente.xml
				emf = Persistence.createEntityManagerFactory("produtos");
			} 
		} catch (RuntimeException ex) {
			throw new DAOException("Erro ao criar o EntityManager " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
		} 
		return emf.createEntityManager();
	}

}
