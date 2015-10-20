//package com.newput.utility;
//
//import javax.ws.rs.core.Response;
//import javax.ws.rs.ext.ExceptionMapper;
//
//import com.newput.domain.TrackerException;
//
//public class TrackerExceptionHandler implements ExceptionMapper<TrackerException> {
//
//	@Override
//	public Response toResponse(TrackerException exception) {
//		// return Response.status(exception.getMessage())
//		// .entity(new ErrorMessage(exception))
//		// .type(MediaType.APPLICATION_JSON).
//		// build();
//		Response.Status status;
////
//		status = Response.Status.INTERNAL_SERVER_ERROR;
//
//		return Response.status(status).header("exception", exception.getMessage()).build();
//	}
//}
