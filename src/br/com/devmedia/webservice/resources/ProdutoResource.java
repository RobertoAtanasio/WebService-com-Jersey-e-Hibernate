package br.com.devmedia.webservice.resources;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import br.com.devmedia.webservice.model.domain.Produto;
import br.com.devmedia.webservice.service.ProdutoService;

// as anotações abaixo são gerenciadas pelo JERSEY 

@Path("/produtos")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@PermitAll
public class ProdutoResource {

	private final ProdutoService produtoService = new ProdutoService();

//	@GET
//	public List<Produto> getProdutos(@BeanParam ProdutoFilterBean produtoFilter) {
//		if ((produtoFilter.getOffset() >= 0) && (produtoFilter.getLimit() > 0)) {
//			return produtoService.getProdutosByPagination(produtoFilter.getOffset(), produtoFilter.getLimit());
//		}
//		if (produtoFilter.getName() != null) {
//			return produtoService.getProdutoByName(produtoFilter.getName());			
//		}
//		
//		return produtoService.getProdutos();
//	}

//	@GET
//	public List<Produto> getProdutos(@QueryParam("name") String name) {
//		if (name != null) {
//			return service.getProdutoByName(name);			
//		}
//		return service.getProdutos();
//	}

//	@GET
//	@Path("{produtoId}")
//	public Produto getProduto(@PathParam("produtoId") long id) {
//		return produtoService.getProduto(id);
//	}
//	
//	@POST
//	public Response save(Produto produto) { 
//		produto = produtoService.saveProduto(produto);
//		return Response.status(Status.CREATED)
//				.entity(produto)
//				.build();
//	}
//		
//	@PUT
//	@Path("{produtoId}")
//	public void update(@PathParam("produtoId") Long id, Produto produto) {
//		produto.setId(id);
//		produtoService.updateProduto(produto);
//	}
//	
//	@DELETE
//	@Path("{produtoId}")
//	public void delete(@PathParam("produtoId") long id) {
//		produtoService.deleteProduto(id);	
//	}

	// -----------

	@POST
	public Response salvarProduto(@PathParam("marcaId") long marcaId, Produto produto, @Context UriInfo uriInfo) {
		produtoService.salvarProduto(produto, marcaId);
		// retornar em location a URL para a pesquisa do produto gravado associado à
		// marca
		return Response.created(uriInfo.getAbsolutePathBuilder().path(Long.toString(produto.getId())).build())
				.entity(produto).build();
	}

	@GET
	public List<Produto> recuperarProdutos(@PathParam("marcaId") long marcaId,
			@QueryParam("nome") @DefaultValue("") String nome) {
		return (nome.isEmpty()) ? produtoService.recuperarProdutos(marcaId)
				: produtoService.recuperarProdutosPorNome(marcaId, nome);
	}

	@GET
	@Path("{produtoId}")
	public Produto recuperarProdutoPorId(@PathParam("marcaId") long marcaId, @PathParam("produtoId") long produtoId) {
		return produtoService.recuperarProdutoPorId(marcaId, produtoId);
	}

	@PUT
	@Path("{produtoId}")
	@RolesAllowed({ "ADMIN", "SUPERVISOR" })
	public Produto atualizarProduto(@PathParam("marcaId") long marcaId, @PathParam("produtoId") long produtoId,
			Produto produto) {
		produtoService.atualizarProduto(marcaId, produtoId, produto);
		return produto;
	}

	@DELETE
	@Path("{produtoId}")
	@RolesAllowed({ "ADMIN" })
	public void excluirProduto(@PathParam("produtoId") long produtoId) {
		produtoService.excluirProduto(produtoId);
	}

}