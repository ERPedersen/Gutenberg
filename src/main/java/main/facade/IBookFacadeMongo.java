package main.facade;

import main.dto.Book;
import main.dto.Location;
import main.exception.BookNotFoundException;
import main.exception.LocationNotFoundException;

import java.util.List;

public interface IBookFacadeMongo {
    /**
     * Gets a List of books from a latitude and a longitude.
     *
     * @param latitude The latitude.
     * @param longitude The longitude.
     * @param radius The radius of the search.
     * @param limit The limit of Books allowed from the search.
     * @throws BookNotFoundException Is thrown if no books are found.
     */
    public List<Book> getBooksFromLatLong(double latitude, double longitude, int radius, int limit) throws BookNotFoundException;

    /**
     * Returns a List of books an author has written.
     *
     * @param name The name of the author.
     * @param limit The limit of Books allowed from the search.
     * @return The books the author has written.
     * @throws BookNotFoundException Is thrown if no books are found.
     */
    public List<Book> getBooksAndCitiesFromAuthor(String name, int limit) throws BookNotFoundException;

    /**
     * Returns a List of books where cities mentioned in a book is in.
     *
     * @param title The title of a book.
     * @param limit The limit of Books allowed from the search.
     * @return The books where the cities are mentioned.
     * @throws LocationNotFoundException Is thrown if no books are found.
     */
    public List<Location> getCitiesFromBook(String title, int limit) throws LocationNotFoundException;

    /**
     * Returns a List of books which has a specific location somewhere in the text.
     *
     * @param name The name of the location that is mentioned.
     * @param limit The limit of Books allowed from the search.
     * @return The books where the location is mentioned.
     * @throws BookNotFoundException Is thrown if no books are found.
     */
    public List<Book> getAuthorsAndBookFromCity(String name, int limit) throws BookNotFoundException;

}
