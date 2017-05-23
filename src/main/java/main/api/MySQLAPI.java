package main.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dto.Book;
import main.dto.Location;
import main.exception.BookNotFoundException;
import main.facade.BookFacadeMySQL;
import main.facade.IBookFacadeMySQL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Path("mysql")
public class MySQLAPI {

	private Gson gson;
	private IBookFacadeMySQL facade;

	@Context
	private UriInfo context;

	/**
	 * Creates a new instance of MySQLAPI
	 */
	public MySQLAPI() {
		gson = new GsonBuilder()
				.setPrettyPrinting()
				.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
				.create();
		facade = new BookFacadeMySQL();
	}

	/**
	 * Root endpoint.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoot() {

		Map<String, Object> map = new HashMap<>();

		map.put("code", "200");
		map.put("msg", "You have successfully connected to the MySQL API!");

		return Response
				.status(Response.Status.OK)
				.entity(gson.toJson(map))
				.build();
	}

	/**
	 * Takes latitude and longitude and returns all books that mention a city at
	 * that coordinate.
	 *
	 * @param latitude  Float the latitude of the location.
	 * @param longitude Float the longitude of the location.
	 * @param radius    Integer the radius of the location.
	 * @return Response object with JSON data.
	 */
	@GET
	@Path("book/location")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBooksFromLatLong(
			@QueryParam("lat") double latitude,
			@QueryParam("long") double longitude,
			@QueryParam("rad") int radius) {

		List<Book> books;

		try {
			books = facade.getBooksFromLatLong(latitude, longitude, radius);
		} catch (BookNotFoundException ex) {
			return Response
					.status(Response.Status.NO_CONTENT)
					.entity(gson.toJson(getErrorResponse(204, ex.getMessage())))
					.build();
		}

		Map<String, Object> map = new HashMap<>();
		map.put("data", books);

		return Response
				.status(Response.Status.OK)
				.entity(gson.toJson(map))
				.build();
	}

	/**
	 * Enables fuzzy searching of authors.
	 *
	 * @param author String the partial name of an author.
	 * @return Response object with Page JSON data.
	 */
	@GET
	@Path("search/author")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAuthors(@QueryParam("q") String author) {

		Map<String, Object> map;

		try {
			map = new HashMap<>();
			map.put("type", "author");
			map.put("data", facade.getFuzzySearchAuthor(author));

		} catch (BookNotFoundException ex) {
			return Response
					.status(Response.Status.NO_CONTENT)
					.entity(gson.toJson(getErrorResponse(204, ex.getMessage())))
					.build();
		}

		return Response
				.status(Response.Status.OK)
				.entity(gson.toJson(map))
				.build();

	}

	/**
	 * Enables fuzzy searching of cities.
	 *
	 * @param city String the partial name of a city.
	 * @return Response object with Page JSON data.
	 */
	@GET
	@Path("search/city")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCities(@QueryParam("q") String city) {

		Map<String, Object> map;

		try {
			map = new HashMap<>();
			map.put("type", "city");
			map.put("data", facade.getFuzzySearchCity(city));

		} catch (BookNotFoundException ex) {
			return Response
					.status(Response.Status.NO_CONTENT)
					.entity(gson.toJson(getErrorResponse(204, ex.getMessage())))
					.build();
		}

		return Response
				.status(Response.Status.OK)
				.entity(gson.toJson(map))
				.build();
	}

	/**
	 * Enables fuzzy searching of books.
	 *
	 * @param title The partial name of a book.
	 * @return Response object with book titles.
	 */
	@GET
	@Path("search/book")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBooks(@QueryParam("q") String title) {

		Map<String, Object> map;

		try {
			map = new HashMap<>();
			map.put("type", "book");
			map.put("data", facade.getFuzzySearchBook(title));

		} catch (BookNotFoundException ex) {
			return Response
					.status(Response.Status.NO_CONTENT)
					.entity(gson.toJson(getErrorResponse(204, ex.getMessage())))
					.build();
		}

		return Response
				.status(Response.Status.OK)
				.entity(gson.toJson(map))
				.build();
	}

	/**
	 * Takes an author name and returns all books by that author, along with all
	 * cities mentioned in those books.
	 *
	 * @param author String the author's name.
	 * @return Response object with JSON data.
	 */
	@GET
	@Path("book/author")
	@Produces(MediaType.APPLICATION_JSON)
	public Response booksAndCitiesFromAuthor(@QueryParam("q") String author) {

		List<Book> books;

		try {
			books = facade.getBooksAndCitiesFromAuthor(author);
		} catch (BookNotFoundException ex) {
			return Response
					.status(Response.Status.NO_CONTENT)
					.entity(gson.toJson(getErrorResponse(204, ex.getMessage())))
					.build();
		}

		Map<String, Object> map = new HashMap<>();
		map.put("data", books);

		return Response
				.status(Response.Status.OK)
				.entity(gson.toJson(map))
				.build();
	}

	/**
	 * Takes a city name, and returns all books which mention the city.
	 *
	 * @param city String name of the city.
	 * @return Response object with JSON data.
	 */
	@GET
	@Path("book/city")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBooksFromCity(@QueryParam("q") String city) {
		List<Book> books;

		try {
			books = facade.getAuthorsAndBookFromCity(city);
		} catch (BookNotFoundException ex) {
			return Response
					.status(Response.Status.NO_CONTENT)
					.entity(gson.toJson(getErrorResponse(204, ex.getMessage())))
					.build();
		}

		Map<String, Object> map = new HashMap<>();
		map.put("data", books);

		return Response
				.status(Response.Status.OK)
				.entity(gson.toJson(map))
				.build();
	}

	/**
	 * Takes a book name, and finds all cities mentioned in that book.
	 *
	 * @param title Title of the book.
	 * @return Response object with JSON locations.
	 */
	@GET
	@Path("location")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLocationsFromBook(@QueryParam("q") String title) {
		List<Location> locations;

		try {
			locations = facade.getCitiesFromBook(title);
		} catch (BookNotFoundException ex) {
			return Response
					.status(Response.Status.NO_CONTENT)
					.entity(gson.toJson(getErrorResponse(204, ex.getMessage())))
					.build();
		}

		Map<String, Object> map = new HashMap<>();
		map.put("data", locations);

		return Response
				.status(Response.Status.OK)
				.entity(gson.toJson(map))
				.build();
	}

	/**
	 * Forms an error response.
	 *
	 * @param code The HTTP code of the response.
	 * @param message The message of the response.
	 * @return Error response
	 */
	private Map<String, Object> getErrorResponse(int code, String message) {
		Map<String, Object> map = new HashMap<>();
		map.put("code", code);
		map.put("msg", message);
		map.put("data", new ArrayList<>());

		return map;
	}

}
