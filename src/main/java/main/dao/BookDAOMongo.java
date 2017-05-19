package main.dao;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import main.dto.Author;
import main.dto.Book;
import main.dto.Location;
import main.exception.ConnectionAlreadyClosedException;
import main.util.DBConnectorMongo;

import static com.mongodb.client.model.Filters.*;


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
     * @param latitude  String The latitude of the location.
     * @param longitude String The longitude of the location.
     * @param radius    The radius where locations are searched.
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

        List<Book> books = new ArrayList<>();

        for (Document dbObject : collection.find(eq("author", new Document("$elemMatch", new Document("name", name))))) {

            books.add(new Book((int) dbObject.get("UID"), (String) dbObject.get("title"), (List<Author>) dbObject.get("authors"), (List<Location>) dbObject.get("locations"), (String) dbObject.get("text")));
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

        collection = db.getCollection("books");

        List<Document> books = (List<Document>) collection.find(eq("title", title)).into(new ArrayList<Document>());
        List<Location> cities = new ArrayList<>();

        for (Document book : books) {

            List<Document> locations = (List<Document>) book.get("locations");
            for (Document loc : locations) {
                int id = (int) loc.get("UID");
                if (loc.get("latitude") instanceof String) {
                    String latitude = (String) loc.get("latitude");
                    String longitude = (String) loc.get("longitude");
                    cities.add(new Location(Long.parseLong("" + id), Double.parseDouble(latitude), Double.parseDouble(longitude), (String) loc.get("name")));
                } else {
                    cities.add(new Location(Long.parseLong("" + id), (double) loc.get("latitude"), (double) loc.get("longitude"), (String) loc.get("name")));
                }
            }
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

}
