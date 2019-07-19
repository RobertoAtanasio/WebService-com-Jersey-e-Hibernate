package br.com.devmedia.webservice.service;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.devmedia.webservice.model.dao.JPAUtil;
import br.com.devmedia.webservice.model.dao.MarcaDAO;
import br.com.devmedia.webservice.model.domain.Marca;
import br.com.devmedia.webservice.utils.IdUtils;

public class MarcaService {

	private final MarcaDAO marcaDAO = new MarcaDAO();

    public void salvarMarca(Marca marca) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            marcaDAO.salvarMarca(marca, em);
            if (marca.getProdutos() != null) {
                marca.getProdutos()
                        .parallelStream()
                        .forEach(marca::addProdutoToMarca);
            }

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Marca> recuperarMarcas() {
    	List<Marca> lista = marcaDAO.recuperarMarcas();
        return lista;
    }

    public Marca recuperarMarcaPorId(long marcaId) {
        EntityManager em = JPAUtil.getEntityManager();
        return marcaDAO.recuperarMarcaPorId(IdUtils.idValido(marcaId), em);
    }

    public List<Marca> recuperarMarcasPorNome(String nome) {
        return marcaDAO.recuperarMarcasPorNome(nome);
    }

    public void atualizarMarca(Marca marca, long marcaId) {
        EntityManager em = JPAUtil.getEntityManager();

        em.getTransaction().begin();
        marca.setId(IdUtils.idValido(marcaId));
        marcaDAO.recuperarMarcaPorId(marcaId, em);
        marcaDAO.atualizarMarca(marca, em);
        em.getTransaction().commit();
    }

    public void excluirMarca(long marcaId) {
        marcaDAO.excluirMarca(IdUtils.idValido(marcaId));
    }
}
