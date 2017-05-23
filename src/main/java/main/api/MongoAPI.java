/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dto.Book;
import main.dto.Location;
import main.exception.BookNotFoundException;
import main.facade.BookFacadeMongo;
import main.facade.IBookFacadeMongo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/mongo")
public class MongoAPI {

    private Gson gson;
    private IBookFacadeMongo facade;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MongoAPI
     */
    public MongoAPI() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create();
        facade = new BookFacadeMongo();
    }

    /**
     * Root endpoint.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoot() {

        Map<String, Object> map = new HashMap<>();

        map.put("code", "200");
        map.put("msg", "You have successfully connected to the Mongo API!");

        return Response
                .status(Response.Status.OK)
                .entity(gson.toJson(map))
                .build();
    }

    /**
     * Takes latitude and longitude and returns all books that mention a city at
     * that coordinate.
     *
     * @param latitude Float the latitude of the location.
     * @param longitude Float the longitude of the location.
     * @param radius Integer the radius of the location.
     * @return Response object with JSON data.
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
     * Takes an author name and returns all books by that author, along with all
     * cities mentioned in those books.
     *
     * @param author String the author's name.
     * @return Response object with JSON data.
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
     * @param bookName String name of the book.
     * @return Response object with JSON data.
     */
    @GET
    @Path("location")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLocationsFromBook(
            @QueryParam("q") String bookName,
            @QueryParam("lim") int limit) {
        List<Location> cities;
        try {
            cities = facade.getCitiesFromBook(bookName, limit);
        } catch (BookNotFoundException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(gson.toJson(ErrorResponse.getErrorResponse(400, ex.getMessage())))
                    .build();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("data", cities);

        return Response
                .status(Response.Status.OK)
                .entity(gson.toJson(map))
                .build();
    }

    /**
     * Takes a city name, and returns all books which mention the city.
     *
     * @param cityName String name of the city.
     * @return Response object with JSON data.
     */
    @GET
    @Path("book/city")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooksFromCity(
            @QueryParam("q") String cityName,
            @QueryParam("lim") int limit) {
        List<Book> books;
        try {
            books = facade.getAuthorsAndBookFromCity(cityName, limit);
        } catch (BookNotFoundException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(gson.toJson(ErrorResponse.getErrorResponse(400, ex.getMessage())))
                    .build();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("data", books);

        return Response
                .status(Response.Status.OK)
                .entity(gson.toJson(map))
                .build();
    }
}