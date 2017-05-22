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
}
