package br.com.devmedia.webservice.service.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.devmedia.webservice.exceptions.IdInvalidoException;
import br.com.devmedia.webservice.model.domain.ErrorMessage;

@Provider
public class IdInvalidoExceptionMapper implements ExceptionMapper<IdInvalidoException> {

	@Override
	public Response toResponse(IdInvalidoException ex) {
		ErrorMessage error = new ErrorMessage(ex.getMessage(), Response.Status.BAD_REQUEST.getStatusCode());
		return Response.status(Response.Status.BAD_REQUEST)
				.entity(error)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
}
