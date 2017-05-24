package main.dao;

import main.dto.Author;
import main.dto.Book;
import main.dto.Location;

import java.util.List;

public interface IBookDAOMySQL {
    /**
     * Gets a List of books from a latitude and a longitude.
     *
     * @param latitude The latitude.
     * @param longitude The longitude.
     * @param radius The radius to search within.
     * @return Books in the vicinity of the lat long position that is searched for.
     */
    public List<Book> getBooksFromLatLong(double latitude, double longitude, int radius);

    /**
     * Returns a List of books an author has written.
     *
     * @param name The name of the author.
     * @return The books the author has written.
     */
    public List<Book> getBooksAndCitiesFromAuthor(String name);

    /**
     * Returns a List of books where cities mentioned in a book is in.
     *
     * @param title The title of a book.
     * @return The books where the cities are mentioned.
     */
    public List<Location> getCitiesFromBook(String title);

    /**
     * Returns a List of books which has a specific location somewhere in the text.
     *
     * @param name The name of he location that is mentioned.
     * @return The books where the location is mentioned.
     */
    public List<Book> getAuthorsAndBooksFromCity(String name);

    /**
     * Returns a List of Authors from a partial name.
     *
     * @param name Partial name of an author.
     * @return Author names.
     */
    public List<String> searchForAuthor(String name);

    /**
     * Returns a List of Books from a partial title.
     *
     * @param title Partial title of a book.
     * @return Book titles.
     */
    public List<String> searchForBook(String title);

    /**
     * Returns a List of Cities from a partial name.
     *
     * @param name Partial name of a city.
     * @return City names.
     */
    public List<String> searchForCity(String name);
}
