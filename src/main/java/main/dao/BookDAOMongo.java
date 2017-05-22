package main.dao;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import main.dto.Author;
import main.dto.Book;
import main.dto.Location;
import main.exception.ConnectionAlreadyClosedException;
import main.util.DBConnectorMongo;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ebbe Nielsen on 12/05/2017.
 */
public class BookDAOMongo implements IBookDAO {

    DBConnectorMongo connector;
    MongoCollection<Document> collection;
    MongoDatabase db;



    /**
     * Default Constructor.
     */
    public BookDAOMongo() {
        connector = new DBConnectorMongo();
        db = connector.getConnection();
    }

    /**
     * Constructor with dependency injection.
     *
     * @param connector DBConnectorMongo The connector to the Mongo database.
     */
    public BookDAOMongo(DBConnectorMongo connector) {
        this.connector = connector;
    }

    /**
     * Returns a List of books from the Mongo database which have the location of a latitude and longitude mentioned.
     *
     * @param latitude String The latitude of the location.
     * @param longitude String The longitude of the location.
     * @param radius The radius where locations are searched.
     * @return List of books The list of books where the location is mentioned.
     */
    @Override
    public List<Book> getBooksFromLatLong(double latitude, double longitude, int radius) {
        return null;
    }

    /**
     * Returns a List of books from the Mongo database which is written by the author.
     *
     * @param name String The name of the author who has written the books.
     * @return List of books The books which are written by the author.
     */
    @Override
    public List<Book> getBooksAndCitiesFromAuthor(String name) {

        collection = db.getCollection("books");

        AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                    new Document("author", new Document("$elemMatch", new Document("name", name)))
        ));

        List<Book> books = new ArrayList<>();

        for (Document dbObject : output) {

            books.add(new Book((long)dbObject.get("UID"),(String)dbObject.get("title"),(List<Author>)dbObject.get("authors"),(List<Location>)dbObject.get("locations"),(String)dbObject.get("text")));
        }

        return books;
    }

    /**
     * Returns a List of books from the Mongo database where the cities mentioned in a Book is mentioned.
     *
     * @param title String The title of the book where locations are searched.
     * @return List of books with locations.
     */
    @Override
    public List<Location> getCitiesFromBook(String title) {

        db = connector.getConnection();
        collection = db.getCollection("books");

        AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                new Document("title", title)
        ));

        List<Location> cities = new ArrayList<>();

        for (Document dbObject : output) {

            //cities.add(dbObject.get("user") + ", " + dbObject.get("tweet_count"));
           // cities.add(new Location(dbObject.get("UID"),dbObject.get("title"),dbObject.get("author")));
        }

        return cities;
    }

    /**
     * Returns a List of books from the Mongo database which has a location mentioned somewhere in the book.
     *
     * @param name String The name of the location that is mentioned in the books.
     * @return List of books The books where the location is mentioned.
     */
    @Override
    public List<Book> getAuthorsAndBooksFromCity(String name) {
        return null;
    }

    /**
     * Unused.
     *
     * @param name
     * @return
     * @throws ConnectionAlreadyClosedException
     */
    @Override
    public List<String> getFuzzySearchAuthor(String name) throws ConnectionAlreadyClosedException {
        throw new UnsupportedOperationException("Fuzzy search is not supported by the mongo api");
    }

    /**
     * Unused.
     *
     * @param title
     * @return
     * @throws ConnectionAlreadyClosedException
     */
    @Override
    public List<String> getFuzzySearchBook(String title) throws ConnectionAlreadyClosedException {
        throw new UnsupportedOperationException("Fuzzy search is not supported by the mongo api");
    }

    /**
     * Unused.
     *
     * @param name
     * @return
     * @throws ConnectionAlreadyClosedException
     */
    @Override
    public List<String> getFuzzySearchCity(String name) throws ConnectionAlreadyClosedException {
        throw new UnsupportedOperationException("Fuzzy search is not supported by the mongo api");
    }
}
