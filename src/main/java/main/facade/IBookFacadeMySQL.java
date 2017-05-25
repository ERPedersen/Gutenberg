package main.facade;

import main.dto.Book;
import main.dto.Location;
import main.exception.AuthorNotFoundException;
import main.exception.BookNotFoundException;
import main.exception.LocationNotFoundException;

import java.util.List;

public interface IBookFacadeMySQL {
    /**
     * Gets a List of books from a latitude and a longitude.
     *
     * @param latitude The latitude.
     * @param longitude The longitude.
     * @param radius The radius of the search.
     * @param limit The limit of returned rows.
     * @throws BookNotFoundException Exception is thrown if no books are found.
     */
    public List<Book> getBooksFromLatLong(double latitude, double longitude, int radius, int limit) throws BookNotFoundException;

    /**
     * Returns a List of books an author has written.
     *
     * @param name The name of the author.
     * @return The books the author has written.
     * @param limit The limit of returned rows.
     * @throws BookNotFoundException Is thrown if no books are found.
     */
    public List<Book> getBooksAndCitiesFromAuthor(String name, int limit) throws BookNotFoundException;

    /**
     * Returns a List of books where cities mentioned in a book is in.
     *
     * @param title The title of a book.
     * @return The books where the cities are mentioned.
     * @param limit The limit of returned rows.
     * @throws LocationNotFoundException Is thrown if no books are found.
     */
    public List<Location> getCitiesFromBook(String title, int limit) throws LocationNotFoundException;

    /**
     * Returns a List of books which has a specific location somewhere in the text.
     *
     * @param name The name of the location that is mentioned.
     * @return The books where the location is mentioned.
     * @param limit The limit of returned rows.
     * @throws BookNotFoundException Is thrown if no books are found.
     */
    public List<Book> getAuthorsAndBookFromCity(String name, int limit) throws BookNotFoundException;

    /**
     * Enables searching of author. Returns a list of Strings with author names.
     *
     * @param name The partial name of an author.
     * @return A collection of String names of authors.
     * @param limit The limit of returned rows.
     * @throws AuthorNotFoundException Is thrown if there is no authors with the name.
     */
    public List<String> searchForAuthor(String name, int limit) throws AuthorNotFoundException;

    /**
     * Enables searching of books. Returns a list of Strings with book titles.
     *
     * @param title The partial title of a book.
     * @return A collection of String names of books.
     * @param limit The limit of returned rows.
     * @throws BookNotFoundException Is thrown if there is no Books with the title.
     */
    public List<String> searchForBook(String title, int limit) throws BookNotFoundException;

    /**
     * Enables searching of cities. Returns a list of Strings with city names.
     *
     * @param name The partial name of a city.
     * @return A collection of String titles of cities.
     * @param limit The limit of returned rows.
     * @throws LocationNotFoundException Is thrown if there is no cities with the names.
     */
    public List<String> searchForCity(String name, int limit) throws LocationNotFoundException;
}
