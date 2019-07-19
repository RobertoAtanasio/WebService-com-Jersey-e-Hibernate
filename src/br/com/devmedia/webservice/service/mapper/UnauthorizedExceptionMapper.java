package br.com.devmedia.webservice.service.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.devmedia.webservice.model.domain.ErrorMessage;
import br.com.devmedia.webservice.service.exception.UnauthorizedException;

@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException>{

	@Override
	public Response toResponse(UnauthorizedException ex) {
		ErrorMessage error = new ErrorMessage(ex.getMessage(), Response.Status.FORBIDDEN.getStatusCode());
		return Response.status(Response.Status.FORBIDDEN)
				.entity(error)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
}
