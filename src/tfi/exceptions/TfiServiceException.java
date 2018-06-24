package tfi.exceptions;

import javax.ws.rs.core.Response;

import com.google.gson.JsonObject;

public class TfiServiceException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1656535942395390529L;
	String message;

	public TfiServiceException(String message) {

		super(message);
		this.message = message;
	}

	public Response toResponse() {
		JsonObject jsonResponseObject = new JsonObject();
		jsonResponseObject.addProperty("message", message);
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsonResponseObject.toString())
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
	}
}
