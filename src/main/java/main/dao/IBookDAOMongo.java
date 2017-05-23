package main.dao;

import main.dto.Author;
import main.dto.Book;
import main.dto.Location;

import java.util.List;

public interface IBookDAOMongo {
    /**
     * Gets a List of books from a latitude and a longitude.
     *
     * @param latitude String The latitude.
     * @param longitude String The longitude.
     * @param radius Integer The radius to search within.
     * @param limit Integer The limit on how many results to return.
     * @return List of books.
     */
    public List<Book> getBooksFromLatLong(double latitude, double longitude, int radius, int limit);

    /**
     * Returns a List of books an author has written.
     *
     * @param name String The name of the author.
     * @param limit Integer The limit on how many results to return.
     * @return List of books The books the author has written.
     */
    public List<Book> getBooksAndCitiesFromAuthor(String name, int limit);

    /**
     * Returns a List of books where cities mentioned in a book is in.
     *
     * @param title String The title of a book.
     * @param limit Integer The limit on how many results to return.
     * @return List of books The books where the cities are mentioned.
     */
    public List<Location> getCitiesFromBook(String title, int limit);

    /**
     * Returns a List of books which has a specific location somewhere in the text.
     *
     * @param name String The name of he location that is mentioned.
     * @param limit Integer The limit on how many results to return.
     * @return List of books The books where the location is mentioned.
     */
    public List<Book> getAuthorsAndBooksFromCity(String name, int limit);

}
