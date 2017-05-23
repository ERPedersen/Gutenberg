package main.dao;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import main.dto.Author;
import main.dto.Book;
import main.dto.Location;
import main.util.DBConnectorMongo;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class BookDAOMongo implements IBookDAOMongo {

    DBConnectorMongo connector;
    MongoCollection<Document> collection;
    MongoDatabase db;
    Gson gson = new Gson();



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
     * @param limit Integer The limit on how many results to return.
     * @return List of books The list of books where the location is mentioned.
     */
    @Override
    public List<Book> getBooksFromLatLong(double latitude, double longitude, int radius, int limit) {
        throw new UnsupportedOperationException("Not implemented");
        /*collection = db.getCollection("books");

        List<Book> books = new ArrayList<>();

        double[] latLong = new double[2];
        latLong[0] = latitude;
        latLong[1] = longitude;

        AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                new Document("$geoNear",
                        new Document("near",
                                new Document("type", "Point")
                                        .append("coordinates", latLong))
                                .append("spherical", "true")
                                .append("maxDistance", radius)
                                .append("distanceField", "distance")
                                .append("minDistance", 1))
        ));

        for (Document dbObject : output) {
            System.out.println(dbObject);
        }

        return books;*/
    }

    /**
     * Returns a List of books from the Mongo database which is written by the author.
     *
     * @param name String The name of the author who has written the books.
     * @param limit Integer The limit on how many results to return.
     * @return List of books The books which are written by the author.
     */
    @Override
    public List<Book> getBooksAndCitiesFromAuthor(String name, int limit) {

        collection = db.getCollection("books");

        List<Book> books = new ArrayList<>();

        for (Document dbObject : collection.find(
                eq("author",
                        new Document("$elemMatch",
                                new Document("name", name)))).limit(limit)) {

            books.add(new Book(
                    (int) dbObject.get("UID"),
                    (String) dbObject.get("title"),
                    (List<Author>) dbObject.get("authors"),
                    (List<Location>) dbObject.get("locations"),
                    (String) dbObject.get("text")));
        }

        return books;
    }

    /**
     * Returns a List of books from the Mongo database where the cities mentioned in a Book is mentioned.
     *
     * @param title String The title of the book where locations are searched.
     * @param limit Integer The limit on how many results to return.
     * @return List of books with locations.
     */
    @Override
    public List<Location> getCitiesFromBook(String title, int limit) {

        collection = db.getCollection("books");

        List<Document> books = (List<Document>) collection.find(eq("title", title)).limit(limit).into(new ArrayList<Document>());
        List<Location> cities = new ArrayList<>();

        for (Document book : books) {

            List<Document> locations = (List<Document>) book.get("locations");
            for (Document loc : locations) {
                String uid = (String) loc.get("UID");
                int id = Integer.parseInt(uid);

                String json = loc.toJson();

                Document coords = (Document) loc.get("loc");
                List<Document> latLong = (List<Document>) coords.get("coordinates");
                Object latObj = latLong.get(0);
                Object longObj = latLong.get(1);

                Location newLocation = gson.fromJson(json, Location.class);

                newLocation.setLatitude((double) latObj);
                newLocation.setLongitude((double) longObj);

                cities.add(newLocation);

            }
        }

        return cities;
    }

    /**
     * Returns a List of books from the Mongo database which has a location mentioned somewhere in the book.
     *
     * @param name String The name of the location that is mentioned in the books.
     * @param limit Integer The limit on how many results to return.
     * @return List of books The books where the location is mentioned.
     */
    @Override
    public List<Book> getAuthorsAndBooksFromCity(String name, int limit) {
        collection = db.getCollection("books");

        List<Book> books = new ArrayList<>();

        for (Document dbObject : collection.find(
                eq("locations",
                        new Document("$elemMatch",
                                new Document("name", name)))).limit(limit)) {

            books.add(new Book(
                    (int) dbObject.get("UID"),
                    (String) dbObject.get("title"),
                    (List<Author>) dbObject.get("authors"),
                    (List<Location>) dbObject.get("locations"),
                    (String) dbObject.get("text")));
        }

        return books;
    }
}
