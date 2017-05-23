package main.dao;

import main.dto.Author;
import main.dto.Book;
import main.dto.Location;

import java.util.List;

public interface IBookDAOMongo {
    /**
     * Gets a List of books from a latitude and a longitude.
     *
     * @param latitude The latitude.
     * @param longitude The longitude.
     * @param radius The radius to search within.
     * @param limit The limit on how many results to return.
     * @return books in the vicinity of the lat long position.
     */
    public List<Book> getBooksFromLatLong(double latitude, double longitude, int radius, int limit);

    /**
     * Returns a List of books an author has written.
     *
     * @param name The name of the author.
     * @param limit The limit on how many results to return.
     * @return The books the author has written.
     */
    public List<Book> getBooksAndCitiesFromAuthor(String name, int limit);

    /**
     * Returns a List of books where cities mentioned in a book is in.
     *
     * @param title The title of a book.
     * @param limit The limit on how many results to return.
     * @return The books where the cities are mentioned.
     */
    public List<Location> getCitiesFromBook(String title, int limit);

    /**
     * Returns a List of books which has a specific location somewhere in the text.
     *
     * @param name The name of he location that is mentioned.
     * @param limit The limit on how many results to return.
     * @return The books where the location is mentioned.
     */
    public List<Book> getAuthorsAndBooksFromCity(String name, int limit);

}
