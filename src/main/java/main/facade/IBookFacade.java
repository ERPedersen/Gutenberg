package main.facade;

import main.dto.Book;
import main.dto.Location;
import main.exception.BookNotFoundException;

import java.util.List;

public interface IBookFacade {
    /**
     * Gets a List of books from a latitude and a longitude.
     *
     * @param latitude String The latitude.
     * @param longitude String The longitude.
     * @throws BookNotFoundException Exception is thrown if no books are found.
     */
    public List<Book> getBooksFromLatLong(double latitude, double longitude, int radius) throws BookNotFoundException;

    /**
     * Returns a List of books an author has written.
     *
     * @param name String The name of the author.
     * @return List of books The books the author has written.
     * @throws BookNotFoundException Exception is thrown if no books are found.
     */
    public List<Book> getBooksAndCitiesFromAuthor(String name) throws BookNotFoundException;

    /**
     * Returns a List of books where cities mentioned in a book is in.
     *
     * @param title String The title of a book.
     * @return List of books The books where the cities are mentioned.
     * @throws BookNotFoundException Exception is thrown if no books are found.
     */
    public List<Location> getCitiesFromBook(String title) throws BookNotFoundException;

    /**
     * Returns a List of books which has a specific location somewhere in the text.
     *
     * @param name String The name of the location that is mentioned.
     * @return List of books The books where the location is mentioned.
     * @throws BookNotFoundException Exception is thrown if no books are found.
     */
    public List<Book> getAuthorsAndBookFromCity(String name) throws BookNotFoundException;

    /**
     * Enables fuzzy searching of author. Returns a list of Strings with author names.
     *
     * @param name String The partial name of an author.
     * @return List<String> A collection of String names of authors.
     * @throws BookNotFoundException
     */
    public List<String> getFuzzySearchAuthor(String name) throws BookNotFoundException;

    /**
     * Enables fuzzy searching of books. Returns a list of Strings with book titles.
     *
     * @param title String The partial title of a book.
     * @return List<String> A collection of String names of books.
     * @throws BookNotFoundException
     * @throws ClassNotFoundException
     */
    public List<String> getFuzzySearchBook(String title) throws BookNotFoundException;

    /**
     * Enables fuzzy searching of cities. Returns a list of Strings with city names.
     *
     * @param name String The partial name of a city.
     * @return List<String> A collection of String titles of cities.
     * @throws BookNotFoundException
     */
    public List<String> getFuzzySearchCity(String name) throws BookNotFoundException;
}
