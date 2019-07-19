package br.com.devmedia.webservice.service.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.devmedia.webservice.model.domain.ErrorMessage;
import br.com.devmedia.webservice.service.exception.UnauthenticatedException;

@Provider
public class UnauthenticatedExceptionMapper implements ExceptionMapper<UnauthenticatedException> {

	@Override
	public Response toResponse(UnauthenticatedException ex) {
		ErrorMessage error = new ErrorMessage(ex.getMessage(), Response.Status.UNAUTHORIZED.getStatusCode());
		return Response.status(Response.Status.UNAUTHORIZED)
				.entity(error)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
}
