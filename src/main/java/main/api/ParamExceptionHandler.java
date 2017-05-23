package main.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.ParamException;

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
public class ParamExceptionHandler implements ExceptionMapper<ParamException> {

	@Context
	private HttpHeaders headers;


	private Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
			.create();

	public Response toResponse(ParamException ex){


		Map<String, Object> map = new HashMap<>();
		map.put("code", 400);
		map.put("msg", "Invalid parameters");
		map.put("data", new ArrayList<>());

		return Response
				.status(400)
				.entity(gson.toJson(map))
				.type(MediaType.APPLICATION_JSON).build();
	}
}