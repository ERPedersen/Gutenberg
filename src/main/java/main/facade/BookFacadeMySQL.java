package main.facade;

import main.dao.BookDAOMySQL;
import main.dao.IBookDAOMySQL;
import main.dto.Book;
import main.dto.Location;
import main.exception.BookNotFoundException;

import java.util.List;

/**
 * Class that distributes calls to either the MySQL, or Mongo database through the injection in the constructor.
 */
public class BookFacadeMySQL implements IBookFacadeMySQL
{
    private IBookDAOMySQL dao;

    /**
     * Default constructor
     */
    public BookFacadeMySQL() {
        this.dao = new BookDAOMySQL();
    }

    /**
     * Constructor with dependency injector.
     *
     * @param dao The dao.
     */
    public BookFacadeMySQL(IBookDAOMySQL dao) {
        this.dao = dao;
    }

    /**
     * Gets a list of books from the data access object and returns it.
     *
     * @param latitude The latitude of the location.
     * @param longitude The longitude of the location.
     * @param radius The radius of the search.
     * @return The books that mentions the location.
     * @throws BookNotFoundException If no books mention the location.
     */
    @Override
    public List<Book> getBooksFromLatLong(double latitude, double longitude, int radius) throws BookNotFoundException {
        List<Book> books = dao.getBooksFromLatLong(latitude, longitude, radius);
        if (null == books || books.size() == 0) {
            throw new BookNotFoundException("No Book was found");
        }
        return books;
    }

    /**
     * Gets a list of books from the dao and returns it.
     *
     * @param name the name of the author that is searched for in the database.
     * @return The books that the Author has written.
     * @throws BookNotFoundException If the author has not written any books.
     */
    @Override
    public List<Book> getBooksAndCitiesFromAuthor(String name) throws BookNotFoundException {
        List<Book> books = dao.getBooksAndCitiesFromAuthor(name);
        if (null == books || books.size() == 0) {
            throw new BookNotFoundException("No Book was found");
        }
        return books;
    }

    /**
     * Gets a list of books from the dao and returns it.
     *
     * @param title The title of the book that is searched for in the database.
     * @return The books which mentions cities mentioned in the book.
     * @throws BookNotFoundException If the book doesn't mention any cities.
     */
    @Override
    public List<Location> getCitiesFromBook(String title) throws BookNotFoundException {
        List<Location> books = dao.getCitiesFromBook(title);
        if (null == books || books.size() == 0) {
            throw new BookNotFoundException("No Book was found");
        }
        return books;
    }

    /**
     * Gets a list of books from the dao and returns it.
     *
     * @param name The name that is searched for in the database.
     * @return The books which the location is mentioned in.
     * @throws BookNotFoundException If there is no books that is mentioned on the location.
     */
    @Override
    public List<Book> getAuthorsAndBookFromCity(String name) throws BookNotFoundException {
        List<Book> books = dao.getAuthorsAndBooksFromCity(name);
        if (null == books || books.size() == 0) {
            throw new BookNotFoundException("No Book was found");
        }
        return books;
    }

    /**
     * Gets a list of city names for searching.
     *
     * @param name The partial name of a city.
     * @return A list of Strings for City names.
     * @throws BookNotFoundException If there is no city names matching.
     */
    @Override
    public List<String> searchForCity(String name) throws BookNotFoundException {
        List<String> cities = dao.searchForCity(name);
        if (null == cities || cities.size() == 0) {
            throw new BookNotFoundException("No city was found");
        }
        return cities;
    }

    /**
     *  Gets a list of book names for searching.
     *
     * @param title The partial title of a book.
     * @return A list of Strings for Book titles.
     * @throws BookNotFoundException If there is no Book title matching.
     */
    @Override
    public List<String> searchForBook(String title) throws BookNotFoundException {
        List<String> books = dao.searchForBook(title);
        if (null == books || books.size() == 0) {
            throw new BookNotFoundException("No book was found");
        }
        return books;
    }

    /**
     * Gets a list of author names for searching.
     *
     * @param name The partial name of an author.
     * @return A list of Strings for Author names.
     * @throws BookNotFoundException If there is no author name matching.
     */
    @Override
    public List<String> searchForAuthor(String name) throws BookNotFoundException {
        List<String> authors = dao.searchForAuthor(name);
        if (null == authors || authors.size() == 0) {
            throw new BookNotFoundException("No author was found");
        }
        return authors;
    }
}