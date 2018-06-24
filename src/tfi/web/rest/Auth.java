package tfi.web.rest;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import tfi.exceptions.MissingParamException;
import tfi.exceptions.TfiException;
import tfi.exceptions.TfiExceptionMessages;
import tfi.exceptions.TfiServiceException;
import tfi.utils.DBUTils;
import tfi.web.pojos.TfiResponse;

@Path("/auth")
public class Auth {
	private final static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Auth.class);
	private JsonObject jsonResponseObject;
	List<HashMap<String, Object>> users = null;
	TfiResponse tfiResponse = null;
	Gson gson = new Gson();
	DBUTils dbuTils = null;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@FormParam("user_object") String userObject) {
		jsonResponseObject = new JsonObject();
		String email = null;
		String password = null;
		JsonParser jsonParser = new JsonParser();
		JsonObject userObj = jsonParser.parse(userObject).getAsJsonObject();
		if (userObj.has("password")) {
			password = userObj.get("password").getAsString();
		} else {
			return new MissingParamException(TfiExceptionMessages.UserIdIsNull).toResponse();
		}
		if (userObj.has("email")) {
			email = userObj.get("email").getAsString();
		} else {
			return new MissingParamException(TfiExceptionMessages.EmailIsNull).toResponse();
		}
		if (email == null) {
			jsonResponseObject.addProperty("data", TfiExceptionMessages.EmailIsNull);
			return Response.status(Response.Status.BAD_REQUEST).entity(jsonResponseObject.toString())
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		}
		if (password == null) {
			jsonResponseObject.addProperty("data", TfiExceptionMessages.PasswordIsNull);
			return Response.status(Response.Status.BAD_REQUEST).entity(jsonResponseObject.toString())
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		}
		try {
			dbuTils = new DBUTils();
		} catch (TfiException e1) {
			e1.printStackTrace();
			return new TfiServiceException(e1.getMessage()).toResponse();
		}
		boolean checkEmailExists = false;
		boolean checkPasswordMatches = false;
		try {
			checkEmailExists = checkEmailExists(email);
		} catch (TfiException e) {
			e.printStackTrace();
			return new TfiServiceException(e.getMessage()).toResponse();
		}
		if (checkEmailExists) {
			checkPasswordMatches = checkPasswordMatches(password);
			if (checkPasswordMatches) {
				System.out.println("Login successful");
				return Response.ok(jsonResponseObject.toString()).header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();

			} else {
				jsonResponseObject.addProperty("data", "Password doesn't match");
				return Response.status(Response.Status.BAD_REQUEST).entity(jsonResponseObject.toString())
						.header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
			}
		} else {
			jsonResponseObject.addProperty("data", "Email doesn't exist");
			return Response.status(Response.Status.BAD_REQUEST).entity(jsonResponseObject.toString())
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		}
	}

	private boolean checkPasswordMatches(String password) {
		for (HashMap<String, Object> hashMap : users) {
			if (hashMap.get("password").toString().equals(password)) {
				jsonResponseObject.addProperty("name", hashMap.get("name").toString());
				return true;
			}
		}
		return false;
	}

	private boolean checkEmailExists(String email) throws TfiException {
		String sql = "SELECT id, employee_id, email, city, \"name\", \"role\", program_manager, "
				+ "senior_program_manager, classroom, grade, shift, community, gender, years_with_tfi, password FROM public.\"user\""
				+ " where email = '" + email + "';";
		users = dbuTils.executeQuery(sql);
		if (users.size() > 0)
			return true;
		else
			return false;
	}
}
