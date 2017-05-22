package main.facade;

import main.dao.IBookDAO;
import main.dto.Book;
import main.dto.Location;
import main.exception.BookNotFoundException;

import java.util.List;

/**
 * Class that distributes calls to either the MySQL, or Mongo database through the injection in the constructor.
 */
public class BookFacade implements IBookFacade
{
    private IBookDAO dao;

    /**
     * Constructor with dependency injector.
     *
     * @param dao IBookDAO The dao.
     */
    public BookFacade(IBookDAO dao) {
        this.dao = dao;
    }

    /**
     * Gets a list of books from the data access object and returns it.
     *
     * @param latitude String The latitude of the location.
     * @param longitude String The longitude of the location.
     * @return List of Books The books that mentions the location.
     * @throws BookNotFoundException Exception If no books mention the location.
     */
    @Override
    public List<Book> getBooksFromLatLong(double latitude, double longitude, int radius) throws BookNotFoundException {
        List<Book> books = dao.getBooksFromLatLong(latitude, longitude, radius);
        if (null == books) {
            throw new BookNotFoundException("No Book was found");
        }
        return books;
    }

    /**
     * Gets a list of books from the dao and returns it.
     *
     * @param name String the name of the author that is searched for in the database.
     * @return List of Books The books that the Author has written.
     * @throws BookNotFoundException Exception If the author has not written any books.
     */
    @Override
    public List<Book> getBooksAndCitiesFromAuthor(String name) throws BookNotFoundException {
        List<Book> books = dao.getBooksAndCitiesFromAuthor(name);
        if (null == books) {
            throw new BookNotFoundException("No Book was found");
        }
        return books;
    }

    /**
     * Gets a list of books from the dao and returns it.
     *
     * @param title String The title of the book that is searched for in the database.
     * @return List of Books The books which mentions cities mentioned in the book.
     * @throws BookNotFoundException Exception If the book doesn't mention any cities.
     */
    @Override
    public List<Location> getCitiesFromBook(String title) throws BookNotFoundException {
        List<Location> books = dao.getCitiesFromBook(title);
        if (null == books) {
            throw new BookNotFoundException("No Book was found");
        }
        return books;
    }

    /**
     * Gets a list of books from the dao and returns it.
     *
     * @param name The name that is searched for in the database.
     * @return List of Books The books which the location is mentioned in.
     * @throws BookNotFoundException Exception If there is no books that is mentioned on the location.
     */
    @Override
    public List<Book> getAuthorsAndBookFromCity(String name) throws BookNotFoundException {
        List<Book> books = dao.getAuthorsAndBooksFromCity(name);
        if (null == books) {
            throw new BookNotFoundException("No Book was found");
        }
        return books;
    }

    /**
     * Gets a list of city names for fuzzy searching.
     *
     * @param name String The partial name of a city.
     * @return List<String> A list of Strings for City names.
     * @throws ConnectionAlreadyClosedException
     * @throws BookNotFoundException
     */
    @Override
    public List<String> getFuzzySearchCity(String name) throws BookNotFoundException {
        List<String> cities = dao.getFuzzySearchCity(name);
        if (null == cities || cities.size() == 0) {
            throw new BookNotFoundException("No city was found");
        }
        return cities;
    }

    /**
     *  Gets a list of book names for fuzzy searching.
     *
     * @param title String The partial title of a book.
     * @return List<String> A list of Strings for Book titles.
     * @throws BookNotFoundException
     * @throws ConnectionAlreadyClosedException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public List<String> getFuzzySearchBook(String title) throws BookNotFoundException {
        List<String> books = dao.getFuzzySearchBook(title);
        if (null == books || books.size() == 0) {
            throw new BookNotFoundException("No book was found");
        }
        return books;
    }

    /**
     * Gets a list of author names for fuzzy searching.
     *
     * @param name String The partial name of an author.
     * @return List<String> A list of Strings for Author names.
     * @throws BookNotFoundException
     * @throws ConnectionAlreadyClosedException
     */
    @Override
    public List<String> getFuzzySearchAuthor(String name) throws BookNotFoundException {
        List<String> authors = dao.getFuzzySearchAuthor(name);
        if (null == authors || authors.size() == 0) {
            throw new BookNotFoundException("No author was found");
        }
        return authors;
    }
}
