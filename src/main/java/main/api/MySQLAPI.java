package main.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dto.Book;
import main.dto.Location;
import main.exception.AuthorNotFoundException;
import main.exception.BookNotFoundException;
import main.exception.LocationNotFoundException;
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
	 * Create a new instance of MySQLAPI that is dependent on an instance of a BookFacadeMySQL.
	 *
	 * @param facade The facade.
	 */
	public MySQLAPI(IBookFacadeMySQL facade) {
		gson = new GsonBuilder()
				.setPrettyPrinting()
				.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
				.create();
		this.facade = facade;
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
	 * @param limit The limit of returned rows.
	 * @return Object with books.
	 */
	@GET
	@Path("book/location")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBooksFromLatLong(
			@QueryParam("lat") double latitude,
			@QueryParam("long") double longitude,
			@QueryParam("rad") int radius,
			@QueryParam("lim") int limit) {

		List<Book> books;

		try {
			books = facade.getBooksFromLatLong(latitude, longitude, radius, limit);
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
	 * @param limit The limit of returned rows.
	 * @return Object with JSON data.
	 */
	@GET
	@Path("book/author")
	@Produces(MediaType.APPLICATION_JSON)
	public Response booksAndCitiesFromAuthor(
			@QueryParam("q") String author,
			@QueryParam("lim") int limit) {

		List<Book> books;

		try {
			books = facade.getBooksAndCitiesFromAuthor(author, limit);
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
	 * Takes a book name, and finds all cities mentioned in that book.
	 *
	 * @param title Title of the book.
	 * @param limit The limit of returned rows.
	 * @return Response object with JSON locations.
	 */
	@GET
	@Path("location")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLocationsFromBook(
			@QueryParam("q") String title,
			@QueryParam("lim") int limit) {
		List<Location> locations;

		try {
			locations = facade.getCitiesFromBook(title, limit);
		} catch (LocationNotFoundException ex) {
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
	 * @param limit The limit of returned rows.
	 * @return Object with JSON data.
	 */
	@GET
	@Path("book/city")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBooksFromCity(
			@QueryParam("q") String city,
			@QueryParam("lim") int limit) {
		List<Book> books;

		try {
			books = facade.getAuthorsAndBookFromCity(city, limit);
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
	 * @param limit The limit of returned rows.
	 * @return Objects with author names.
	 */
	@GET
	@Path("search/author")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAuthors(
			@QueryParam("q") String author,
			@QueryParam("lim") int limit) {

		Map<String, Object> map;

		try {
			map = new HashMap<>();
			map.put("type", "author");
			map.put("data", facade.searchForAuthor(author, limit));

		} catch (AuthorNotFoundException ex) {
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
	 * @param limit The limit of returned rows.
	 * @return Object with city names.
	 */
	@GET
	@Path("search/city")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCities(
			@QueryParam("q") String city,
			@QueryParam("lim") int limit) {

		Map<String, Object> map;

		try {
			map = new HashMap<>();
			map.put("type", "city");
			map.put("data", facade.searchForCity(city, limit));

		} catch (LocationNotFoundException ex) {
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
	 * @param limit The limit of returned rows.
	 * @return Object with book titles.
	 */
	@GET
	@Path("search/book")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBooks(
			@QueryParam("q") String title,
			@QueryParam("lim") int limit) {

		Map<String, Object> map;

		try {
			map = new HashMap<>();
			map.put("type", "book");
			map.put("data", facade.searchForBook(title, limit));

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