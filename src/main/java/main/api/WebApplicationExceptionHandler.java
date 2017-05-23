package main.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.WebApplicationException;
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
public class WebApplicationExceptionHandler implements ExceptionMapper<WebApplicationException> {

	@Context
	private HttpHeaders headers;


	private Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
			.create();

	public Response toResponse(WebApplicationException ex){


		Map<String, Object> map = new HashMap<>();
		map.put("code", 500);
		map.put("msg", "An unexpected error occurred.");
		map.put("data", new ArrayList<>());

		return Response
				.status(500)
				.entity(gson.toJson(map))
				.type(MediaType.APPLICATION_JSON).build();
	}
}