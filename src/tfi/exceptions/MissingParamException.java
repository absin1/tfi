package tfi.exceptions;

import javax.ws.rs.core.Response;

import com.google.gson.JsonObject;

public class MissingParamException extends TfiServiceException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6949213355341393598L;
	String message;

	public MissingParamException(String message) {

		super(message);
		this.message = message;
	}

	public Response toResponse() {
		JsonObject jsonResponseObject = new JsonObject();
		jsonResponseObject.addProperty("message", message);
		return Response.status(Response.Status.BAD_REQUEST).entity(jsonResponseObject.toString())
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
	}
}
