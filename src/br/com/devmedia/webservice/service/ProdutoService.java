package br.com.devmedia.webservice.service;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.devmedia.webservice.model.dao.JPAUtil;
import br.com.devmedia.webservice.model.dao.ProdutoDAO;
import br.com.devmedia.webservice.model.domain.Produto;
import br.com.devmedia.webservice.utils.IdUtils;

public class ProdutoService {

	private ProdutoDAO dao = new ProdutoDAO();
	private final ProdutoDAO produtoDAO = new ProdutoDAO();
	
	public List<Produto> getProdutos() {
		return dao.getAll();
	}
	
	public Produto getProduto(Long id) {
		return dao.getById(id);
	}
	
	public Produto saveProduto(Produto produto) {
		return dao.save(produto);
	}
	
	public Produto updateProduto(Produto produto) {
		return dao.update(produto);
	}
	
	public Produto deleteProduto(Long id) {
		return dao.delete(id);
	}
	
	public List<Produto> getProdutosByPagination(int firstResult, int maxResults) {
		return dao.getByPagination(firstResult, maxResults);
	}
	
	public List<Produto> getProdutoByName(String name) {
		return dao.getByName(name);
	}
	
	//-------------
	
	public void salvarProduto(Produto produto, long marcaId) {
        produtoDAO.salvarProduto(produto, IdUtils.idValido(marcaId));
    }
	
	public List<Produto> recuperarProdutos(long marcaId) {
		System.out.println(">>>>> ProdutoService...recuperarProdutos: " + marcaId);
        return produtoDAO.recuperarProdutos(IdUtils.idValido(marcaId));
    }

    public Produto recuperarProdutoPorId(long marcaId, long produtoId) {
        EntityManager em = JPAUtil.getEntityManager();

        em.getTransaction().begin();
        Produto produto = produtoDAO.recuperarProdutoPorId(IdUtils.idValido(marcaId), IdUtils.idValido(produtoId), em);
        em.getTransaction().commit();

        return produto;
    }

    public List<Produto> recuperarProdutosPorNome(long marcaId, String nome) {
        return produtoDAO.recuperarProdutosPorNome(IdUtils.idValido(marcaId), nome);
    }

    public void atualizarProduto(long marcaId, long produtoId, Produto produto) {
        EntityManager em = JPAUtil.getEntityManager();

        em.getTransaction().begin();
        produto.setId(produtoId);
        produtoDAO.recuperarProdutoPorId(IdUtils.idValido(marcaId), IdUtils.idValido(produtoId), em);
        produtoDAO.atualizarProduto(produto, em);
        em.getTransaction().commit();
    }

    public void excluirProduto(long produtoId) {
        produtoDAO.excluirProduto(IdUtils.idValido(produtoId));
    }
}
