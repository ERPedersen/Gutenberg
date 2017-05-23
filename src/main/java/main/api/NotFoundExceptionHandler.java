package main.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.NotFoundException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<NotFoundException> {

	@Context
	private HttpHeaders headers;


	private Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
			.create();

	/**
	 * Handles 404 responses.
	 *
	 * @param ex The exception that is caught.
	 * @return The response if the exception is caught.
	 */
	public Response toResponse(NotFoundException ex){

		Map<String, Object> map = new HashMap<>();
		map.put("code", 404);
		map.put("msg", "Not Found");
		map.put("data", new ArrayList<>());

		return Response
				.status(404)
				.entity(gson.toJson(map))
				.type(MediaType.APPLICATION_JSON).build();
	}
}