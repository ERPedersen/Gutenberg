package main.facade;

import main.dto.Book;
import main.dto.Location;
import main.exception.BookNotFoundException;

import java.util.List;

public interface IBookFacadeMySQL {
    /**
     * Gets a List of books from a latitude and a longitude.
     *
     * @param latitude The latitude.
     * @param longitude The longitude.
     * @param radius The radius of the search.
     * @throws BookNotFoundException Exception is thrown if no books are found.
     */
    public List<Book> getBooksFromLatLong(double latitude, double longitude, int radius) throws BookNotFoundException;

    /**
     * Returns a List of books an author has written.
     *
     * @param name The name of the author.
     * @return The books the author has written.
     * @throws BookNotFoundException Is thrown if no books are found.
     */
    public List<Book> getBooksAndCitiesFromAuthor(String name) throws BookNotFoundException;

    /**
     * Returns a List of books where cities mentioned in a book is in.
     *
     * @param title The title of a book.
     * @return The books where the cities are mentioned.
     * @throws BookNotFoundException Is thrown if no books are found.
     */
    public List<Location> getCitiesFromBook(String title) throws BookNotFoundException;

    /**
     * Returns a List of books which has a specific location somewhere in the text.
     *
     * @param name The name of the location that is mentioned.
     * @return The books where the location is mentioned.
     * @throws BookNotFoundException Is thrown if no books are found.
     */
    public List<Book> getAuthorsAndBookFromCity(String name) throws BookNotFoundException;

    /**
     * Enables fuzzy searching of author. Returns a list of Strings with author names.
     *
     * @param name The partial name of an author.
     * @return A collection of String names of authors.
     * @throws BookNotFoundException Is thrown if there is no authors with the name.
     */
    public List<String> getFuzzySearchAuthor(String name) throws BookNotFoundException;

    /**
     * Enables fuzzy searching of books. Returns a list of Strings with book titles.
     *
     * @param title The partial title of a book.
     * @return A collection of String names of books.
     * @throws BookNotFoundException Is thrown if there is no Books with the title.
     */
    public List<String> getFuzzySearchBook(String title) throws BookNotFoundException;

    /**
     * Enables fuzzy searching of cities. Returns a list of Strings with city names.
     *
     * @param name The partial name of a city.
     * @return A collection of String titles of cities.
     * @throws BookNotFoundException Is thrown if there is no cities with the names.
     */
    public List<String> getFuzzySearchCity(String name) throws BookNotFoundException;
}
