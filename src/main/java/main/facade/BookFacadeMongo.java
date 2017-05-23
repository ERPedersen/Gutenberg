package main.facade;

import main.dao.BookDAOMongo;
import main.dao.IBookDAOMongo;
import main.dto.Book;
import main.dto.Location;
import main.exception.BookNotFoundException;

import java.util.List;

public class BookFacadeMongo implements IBookFacadeMongo {
    private IBookDAOMongo dao;

    /**
     * Default constructor.
     */
    public BookFacadeMongo() {
        dao = new BookDAOMongo();
    }

    /**
     * Constructor with dependency injector.
     *
     * @param dao The dao.
     */
    public BookFacadeMongo(IBookDAOMongo dao) {
        this.dao = dao;
    }

    /**
     * Gets a list of books from the data access object and returns it.
     *
     * @param latitude The latitude of the location.
     * @param longitude The longitude of the location.
     * @param radius The radius of the search.
     * @param limit The maximum amount of results allowed from the query.
     * @return The books that mentions the location.
     * @throws BookNotFoundException Exception If no books mention the location.
     */
    @Override
    public List<Book> getBooksFromLatLong(double latitude, double longitude, int radius, int limit) throws BookNotFoundException {
        List<Book> books = dao.getBooksFromLatLong(latitude, longitude, radius, limit);
        if (null == books || books.size() == 0) {
            throw new BookNotFoundException("No Book was found");
        }
        return books;
    }

    /**
     * Gets a list of books from the dao and returns it.
     *
     * @param name The name of the author that is searched for in the database.
     * @param limit The maximum amount of results allowed from the query.
     * @return The books that the Author has written.
     * @throws BookNotFoundException If the author has not written any books.
     */
    @Override
    public List<Book> getBooksAndCitiesFromAuthor(String name, int limit) throws BookNotFoundException {
        List<Book> books = dao.getBooksAndCitiesFromAuthor(name, limit);
        if (null == books || books.size() == 0) {
            throw new BookNotFoundException("No Book was found");
        }
        return books;
    }

    /**
     * Gets a list of books from the dao and returns it.
     *
     * @param title The title of the book that is searched for in the database.
     * @param limit The maximum amount of results allowed from the query.
     * @return The books which mentions cities mentioned in the book.
     * @throws BookNotFoundException If the book doesn't mention any cities.
     */
    @Override
    public List<Location> getCitiesFromBook(String title, int limit) throws BookNotFoundException {
        List<Location> books = dao.getCitiesFromBook(title, limit);
        if (null == books || books.size() == 0) {
            throw new BookNotFoundException("No Book was found");
        }
        return books;
    }

    /**
     * Gets a list of books from the dao and returns it.
     *
     * @param name The name that is searched for in the database.
     * @param limit The maximum amount of results allowed from the query.
     * @return The books which the location is mentioned in.
     * @throws BookNotFoundException If there is no books that is mentioned on the location.
     */
    @Override
    public List<Book> getAuthorsAndBookFromCity(String name, int limit) throws BookNotFoundException {
        List<Book> books = dao.getAuthorsAndBooksFromCity(name, limit);
        if (null == books || books.size() == 0) {
            throw new BookNotFoundException("No Book was found");
        }
        return books;
    }

}
