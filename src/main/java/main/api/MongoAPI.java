/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import main.dao.BookDAOMySQL;
import main.dao.IBookDAO;
import main.dto.Book;
import main.dto.Location;
import main.exception.BookNotFoundException;

import main.facade.BookFacade;
import main.facade.IBookFacade;

/**
 *
 * @author Private
 */
@Path("/mongo")
public class MongoAPI {

    Gson gson;
    IBookDAO dao;
    IBookFacade facade;

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
        dao = new BookDAOMySQL();
        facade = new BookFacade(dao);
    }

    /**
     * Test endpoint for confirming proper connection.
     */
    @GET
    @Path("test")
    public Response testApi() {

        return Response.status(Response.Status.OK).entity("hey hvasså ska vi smut på grillen").build();
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
    @Path("fromlatlong")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooksFromLatLong(
            @QueryParam("lat") double latitude,
            @QueryParam("long") double longitude,
            @QueryParam("rad") int radius) {

        List<Book> books;
        try {
            books = facade.getBooksFromLatLong(latitude, longitude, radius);
        } catch (BookNotFoundException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(gson.toJson(ex.getMessage())).build();
        }

        return Response.status(Response.Status.OK).entity(gson.toJson(books)).build();
    }

    /**
     * Takes an author name and returns all books by that author, along with all
     * cities mentioned in those books.
     *
     * @param author String the author's name.
     * @return Response object with JSON data.
     */
    @GET
    @Path("fromauthor")
    @Produces(MediaType.APPLICATION_JSON)
    public Response booksAndCitiesFromAuthor(@QueryParam("q") String author) {

        List<Book> books;
        try {
            books = facade.getBooksAndCitiesFromAuthor(author);
        } catch (BookNotFoundException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(gson.toJson(ex.getMessage())).build();
        }

        return Response.status(Response.Status.OK).entity(gson.toJson(books)).build();
    }

    /**
     * Takes a book name, and finds all cities mentioned in that book.
     *
     * @param bookName String name of the book.
     * @return Response object with JSON data.
     */
    @GET
    @Path("frombook")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCitiesFromBook(@QueryParam("q") String bookName) {
        List<Location> cities;
        try {
            cities = facade.getCitiesFromBook(bookName);
        } catch (BookNotFoundException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(gson.toJson(ex.getMessage())).build();
        }

        return Response.status(Response.Status.OK).entity(gson.toJson(cities)).build();
    }

    /**
     * Takes a city name, and returns all books which mention the city.
     *
     * @param cityName String name of the city.
     * @return Response object with JSON data.
     */
    @GET
    @Path("fromcity")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthorsAndBooksFromCity(@QueryParam("q") String cityName) {
        List<Book> books;
        try {
            books = facade.getAuthorsAndBookFromCity(cityName);
        } catch (BookNotFoundException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(gson.toJson(ex.getMessage())).build();
        }

        return Response.status(Response.Status.OK).entity(gson.toJson(books)).build();
    }

}
