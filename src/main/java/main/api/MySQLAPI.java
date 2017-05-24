package main.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dto.Book;
import main.dto.Location;
import main.exception.BookNotFoundException;
import main.facade.BookFacadeMySQL;
import main.facade.IBookFacadeMySQL;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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
	 *
	 * @return The root endpoint of the MySQL API.
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
				.header("Access-Control-Allow-Origin", "*")
				.build();
	}

	/**
	 * Returns books that mention locations within a given radius of a given set of coordinates.
	 *
	 * @param latitude The latitude of the location.
	 * @param longitude The longitude of the location.
	 * @param radius The radius of the location.
	 * @return Object with books.
	 */
	@GET
	@Path("book/location")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBooksFromLatLong(@QueryParam("lat") double latitude, @QueryParam("long") double longitude, @QueryParam("rad") int radius) {

		List<Book> books;

		try {
			books = facade.getBooksFromLatLong(latitude, longitude, radius);
		} catch (BookNotFoundException ex) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(gson.toJson(ErrorResponse.getErrorResponse(400, ex.getMessage())))
					.header("Access-Control-Allow-Origin", "*")
					.build();
		}

		Map<String, Object> map = new HashMap<>();
		map.put("data", books);

		return Response
				.status(Response.Status.OK)
				.entity(gson.toJson(map))
				.header("Access-Control-Allow-Origin", "*")
				.build();
	}

	/**
	 * Takes an author name and returns all books by that author, along with all
	 * cities mentioned in those books.
	 *
	 * @param author The author's name.
	 * @return Object with JSON data.
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
					.status(Response.Status.BAD_REQUEST)
					.entity(gson.toJson(ErrorResponse.getErrorResponse(400, ex.getMessage())))
					.header("Access-Control-Allow-Origin", "*")
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
					.status(Response.Status.BAD_REQUEST)
					.entity(gson.toJson(ErrorResponse.getErrorResponse(400, ex.getMessage())))
					.header("Access-Control-Allow-Origin", "*")
					.build();
		}

		Map<String, Object> map = new HashMap<>();
		map.put("data", locations);

		return Response
				.status(Response.Status.OK)
				.entity(gson.toJson(map))
				.header("Access-Control-Allow-Origin", "*")
				.build();
	}


	/**
	 * Takes a city name, and returns all books which mention the city.
	 *
	 * @param city Name of the city.
	 * @return Object with JSON data.
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
					.status(Response.Status.BAD_REQUEST)
					.entity(gson.toJson(ErrorResponse.getErrorResponse(400, ex.getMessage())))
					.header("Access-Control-Allow-Origin", "*")
					.build();
		}

		Map<String, Object> map = new HashMap<>();
		map.put("data", books);

		return Response
				.status(Response.Status.OK)
				.entity(gson.toJson(map))
				.header("Access-Control-Allow-Origin", "*")
				.build();
	}


	/**
	 * Enables fuzzy searching of authors.
	 *
	 * @param author The partial name of an author.
	 * @return Objects with author names.
	 */
	@GET
	@Path("search/author")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAuthors(@QueryParam("q") String author) {

		Map<String, Object> map;

		try {
			map = new HashMap<>();
			map.put("type", "author");
			map.put("data", facade.searchForAuthor(author));

		} catch (BookNotFoundException ex) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(gson.toJson(ErrorResponse.getErrorResponse(400, ex.getMessage())))
					.header("Access-Control-Allow-Origin", "*")
					.build();
		}

		return Response
				.status(Response.Status.OK)
				.entity(gson.toJson(map))
				.header("Access-Control-Allow-Origin", "*")
				.build();

	}

	/**
	 * Enables fuzzy searching of cities.
	 *
	 * @param city The partial name of a city.
	 * @return Object with city names.
	 */
	@GET
	@Path("search/city")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCities(@QueryParam("q") String city) {

		Map<String, Object> map;

		try {
			map = new HashMap<>();
			map.put("type", "city");
			map.put("data", facade.searchForCity(city));

		} catch (BookNotFoundException ex) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(gson.toJson(ErrorResponse.getErrorResponse(400, ex.getMessage())))
					.header("Access-Control-Allow-Origin", "*")
					.build();
		}

		return Response
				.status(Response.Status.OK)
				.entity(gson.toJson(map))
				.header("Access-Control-Allow-Origin", "*")
				.build();
	}

	/**
	 * Enables fuzzy searching of books.
	 *
	 * @param title The partial name of a book.
	 * @return Object with book titles.
	 */
	@GET
	@Path("search/book")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBooks(@QueryParam("q") String title) {

		Map<String, Object> map;

		try {
			map = new HashMap<>();
			map.put("type", "book");
			map.put("data", facade.searchForBook(title));

		} catch (BookNotFoundException ex) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(gson.toJson(ErrorResponse.getErrorResponse(400, ex.getMessage())))
					.header("Access-Control-Allow-Origin", "*")
					.build();
		}

		return Response
				.status(Response.Status.OK)
				.entity(gson.toJson(map))
				.header("Access-Control-Allow-Origin", "*")
				.build();
	}
}