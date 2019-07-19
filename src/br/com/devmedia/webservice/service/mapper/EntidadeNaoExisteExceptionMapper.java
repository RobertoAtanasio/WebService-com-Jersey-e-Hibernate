package br.com.devmedia.webservice.service.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.devmedia.webservice.exceptions.EntidadeNaoExisteException;
import br.com.devmedia.webservice.model.domain.ErrorMessage;

@Provider
public class EntidadeNaoExisteExceptionMapper implements ExceptionMapper<EntidadeNaoExisteException> {

	@Override
	public Response toResponse(EntidadeNaoExisteException ex) {
		ErrorMessage error = new ErrorMessage(ex.getMessage(), Response.Status.NOT_FOUND.getStatusCode());
		return Response.status(Response.Status.NOT_FOUND)
				.entity(error)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}

}
