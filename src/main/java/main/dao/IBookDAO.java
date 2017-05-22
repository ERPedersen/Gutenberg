package main.dao;

import main.dto.Book;
import main.dto.Location;
import main.exception.ConnectionAlreadyClosedException;

import java.sql.SQLException;
import java.util.List;

public interface IBookDAO {
    /**
     * Gets a List of books from a latitude and a longitude.
     *
     * @param latitude String The latitude.
     * @param longitude String The longitude.
     * @return List of books.
     */
    public List<Book> getBooksFromLatLong(double latitude, double longitude, int radius) throws ConnectionAlreadyClosedException;

    /**
     * Returns a List of books an author has written.
     *
     * @param name String The name of the author.
     * @return List of books The books the author has written.
     */
    public List<Book> getBooksAndCitiesFromAuthor(String name) throws ConnectionAlreadyClosedException;

    /**
     * Returns a List of books where cities mentioned in a book is in.
     *
     * @param title String The title of a book.
     * @return List of books The books where the cities are mentioned.
     */
    public List<Location> getCitiesFromBook(String title) throws ConnectionAlreadyClosedException;

    /**
     * Returns a List of books which has a specific location somewhere in the text.
     *
     * @param name String The name of he location that is mentioned.
     * @return List of books The books where the location is mentioned.
     */
    public List<Book> getAuthorsAndBooksFromCity(String name) throws SQLException, ClassNotFoundException, ConnectionAlreadyClosedException;

    /**
     * Returns a List of Authors from a partial name.
     *
     * @param name String partial name of an author.
     * @return List<String> List of String author names.
     * @throws ConnectionAlreadyClosedException
     */
    public List<String> getFuzzySearchAuthor(String name) throws ConnectionAlreadyClosedException;

    /**
     * Returns a List of Books from a partial title.
     *
     * @param title String partial title of a book.
     * @return List<String> List of String book titles.
     * @throws ConnectionAlreadyClosedException
     */
    public List<String> getFuzzySearchBook(String title) throws ConnectionAlreadyClosedException;

    /**
     * Returns a List of Cities from a partial name.
     *
     * @param name String partial name of a city.
     * @return List<String> List of String city names.
     * @throws ConnectionAlreadyClosedException
     */
    public List<String> getFuzzySearchCity(String name) throws ConnectionAlreadyClosedException;
}
