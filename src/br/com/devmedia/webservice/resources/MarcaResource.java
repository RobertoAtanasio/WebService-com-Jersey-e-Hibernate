package br.com.devmedia.webservice.resources;

import java.util.List;
import java.util.stream.Collectors;

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

import br.com.devmedia.webservice.model.domain.Marca;
import br.com.devmedia.webservice.model.domain.dto.MarcaDTO;
import br.com.devmedia.webservice.service.MarcaService;

@Path("/marcas")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@PermitAll
public class MarcaResource {

	private final MarcaService marcaService = new MarcaService();

    @POST
    public Response salvarMarca(Marca marca, @Context UriInfo uriInfo) {
        marcaService.salvarMarca(marca);

        return Response
                .created(uriInfo.getAbsolutePathBuilder().path(Long.toString(marca.getId())).build())
                .entity(marca)
                .build();
    }

    @GET
    public List<MarcaDTO> recuperarMarcas(@QueryParam("nome") @DefaultValue("") String nome) {
    	if (nome.isEmpty()) {
    		List<MarcaDTO> lista = marcaService.recuperarMarcas().stream().map(MarcaDTO::criarDTO).collect(Collectors.toList());
    		return lista;
    	}
    	return marcaService.recuperarMarcasPorNome(nome).stream().map(MarcaDTO::criarDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/produtos")
    public List<Marca> recuperarMarcasProduto(@QueryParam("nome") @DefaultValue("") String nome) {
    	return (nome.isEmpty()) ? marcaService.recuperarMarcas() : marcaService.recuperarMarcasPorNome(nome);
    }

    @GET
    @Path("{marcaId}")
    public Marca recuperarMarcaPorId(@PathParam("marcaId") long marcaId) {
        return marcaService.recuperarMarcaPorId(marcaId);
    }

    @PUT
    @Path("{marcaId}")
    @RolesAllowed({"ADMIN", "SUPERVISOR"})
    public Marca atualizarMarca(@PathParam("marcaId") long marcaId, Marca marca) {
        marcaService.atualizarMarca(marca, marcaId);
        return marca;
    }

    @DELETE
    @Path("{marcaId}")
    @RolesAllowed({"ADMIN"})
    public void excluirMarca(@PathParam("marcaId") long marcaId) {
        marcaService.excluirMarca(marcaId);
    }

    @Path("{marcaId}/produtos")
    public ProdutoResource obterProdutoResource() {
        return new ProdutoResource();
    }
}
