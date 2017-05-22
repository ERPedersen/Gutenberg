package main.dao;

import main.dto.Author;
import main.dto.Book;
import main.dto.Location;
import main.util.DBConnectorMySQL;
import main.util.IDBConnectorMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookDAOMySQL implements IBookDAO {

    private IDBConnectorMySQL connector;

    /**
     * Default constructor.
     */
    public BookDAOMySQL() {
        connector = new DBConnectorMySQL();
    }

    /**
     * Constructor with dependency injection.
     *
     * @param connector IDBConnectorMySQL The connector to the MYSQL database.
     */
    public BookDAOMySQL(IDBConnectorMySQL connector) {
        this.connector = connector;
    }

    /**
     * Returns a List of books from the MYSQL database which have the location of a latitude and longitude mentioned.
     *
     * @param latitude  Double the latitude of the location.
     * @param longitude Double the longitude of the location.
     * @param radius    Int The radius the locations are found from.
     * @return List of books The list of books where the location is mentioned.
     */
    @Override
    public List<Book> getBooksFromLatLong(double latitude, double longitude, int radius) {
        List<Book> books = new ArrayList<>();
        String queryString = "" +
                "SELECT b.b_id, b.title, b.text, a.a_id, a.name, l1.l_id, l1.latitude, l1.longitude, l1.name " +
                "FROM book b " +
                "JOIN author_book ab ON b.b_id = ab.b_id " +
                "JOIN author a ON ab.a_id = a.a_id " +
                "JOIN book_location bl ON b.b_id = bl.b_id " +
                "JOIN location l1 ON bl.l_id = l1.l_id " +
                "WHERE l1.l_id IN(" +
                "SELECT l2.l_id " +
                "FROM location l2 " +
                "WHERE(" +
                "6371 * acos(" +
                "cos(radians(?)) " +
                "* cos(radians(l2.latitude)) " +
                "* cos(radians(l2.longitude) - radians(?)) " +
                "+ sin ( radians(?) ) " +
                "* sin(radians( l2.latitude ))" +
                ")) < ?" +
                ");";

        try {
            Connection con = connector.getConnection();
            PreparedStatement statement = con.prepareStatement(queryString);
            statement.setDouble(1, latitude);
            statement.setDouble(2, longitude);
            statement.setDouble(3, latitude);
            statement.setInt(4, radius);

            ResultSet resultSet = statement.executeQuery();

            Book book = null;
            long currentBookUID = 0L;

            while (resultSet.next()) {
                if (currentBookUID != resultSet.getLong(1)) {
                    currentBookUID = resultSet.getLong(1);
                    book = new Book(
                            resultSet.getLong(1),
                            resultSet.getString(2),
                            new ArrayList<>(),
                            new ArrayList<>(),
                            new ArrayList<>(),
                            resultSet.getString(3)
                    );
                    books.add(book);
                    book.addAuthor(new Author(resultSet.getLong(4), resultSet.getString(5)));
                }

                if (book != null) {
                    book.addLocationWithinRadius(new Location(
                            resultSet.getLong(6),
                            resultSet.getDouble(7),
                            resultSet.getDouble(8),
                            resultSet.getString(9)
                    ));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            if (Objects.equals(System.getenv("PROCESS_ENV"), "prod")) {
                return null;
            } else {
                e.printStackTrace();
            }
        } finally {
            connector.closeConnection();
        }
        return books;
    }

    /**
     * Returns a List of books from the MYSQL database which is written by the author.
     *
     * @param name String The name of the author who has written the books.
     * @return List of books which are written by the author.
     */
    @Override
    public List<Book> getBooksAndCitiesFromAuthor(String name) {
        List<Book> books = new ArrayList<>();
        String queryString =
                "SELECT DISTINCT b.b_id, b.title, b.text, a.a_id, l.l_id, l.latitude, l.longitude, l.name " +
                        "FROM book b " +
                        "JOIN book_location bl ON b.b_id = bl.b_id " +
                        "JOIN location l ON bl.l_id = l.l_id " +
                        "JOIN author_book ab ON b.b_id = ab.b_id " +
                        "JOIN author a ON ab.a_id = a.a_id " +
                        "WHERE a.name = ?" +
                        "ORDER BY b.b_id;";
        try {
            Connection con = connector.getConnection();
            PreparedStatement statement = con.prepareStatement(queryString);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            Book book = null;
            long currentBookUID = 0L;

            while (resultSet.next()) {

                if (currentBookUID != resultSet.getLong(1)) {
                    currentBookUID = resultSet.getLong(1);

                    book = new Book(
                            resultSet.getLong(1),
                            resultSet.getString(2),
                            new ArrayList<>(),
                            new ArrayList<>(),
                            resultSet.getString(3)
                    );
                    books.add(book);
                    book.addAuthor(new Author(resultSet.getLong(4), name));
                }

                if (book != null) {
                    book.addLocation(new Location(
                            resultSet.getLong(5),
                            resultSet.getDouble(6),
                            resultSet.getDouble(7),
                            resultSet.getString(8)
                    ));
                }

            }
        } catch (SQLException | ClassNotFoundException e) {
            if (Objects.equals(System.getenv("PROCESS_ENV"), "prod")) {
                return null;
            } else {
                e.printStackTrace();
            }
        } finally {
            connector.closeConnection();
        }

        return books;
    }

    /**
     * Returns a List of books from the MYSQL database where the cities mentioned in a Book is mentioned.
     *
     * @param title String The title of the book.
     * @return List of books with locations.
     */
    @Override
    public List<Location> getCitiesFromBook(String title) {
        List<Location> locations = new ArrayList<>();
        String queryString =
                "SELECT * FROM location l " +
                        "JOIN book_location bl ON l.l_id = bl.l_id " +
                        "JOIN book b ON bl.b_id = b.b_id " +
                        "WHERE MATCH(b.title) AGAINST(?) AND b.title LIKE ?;";

        try {
            Connection con = connector.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, title);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Location location = new Location(
                        resultSet.getLong(1),
                        resultSet.getDouble(2),
                        resultSet.getDouble(3),
                        resultSet.getString(4)
                );
                locations.add(location);
            }

        } catch (SQLException | ClassNotFoundException e) {
            if (Objects.equals(System.getenv("PROCESS_ENV"), "prod")) {
                return null;
            } else {
                e.printStackTrace();
            }
        } finally {
            connector.closeConnection();
        }

        return locations;

    }

    /**
     * Returns a List of books from the MYSQL database which has a location mentioned somewhere in the book.
     *
     * @param name String the name of the location that is mentioned in the books.
     * @return List of books The books where the location is mentioned.
     */
    @Override
    public List<Book> getAuthorsAndBooksFromCity(String name) {
        List<Book> books = new ArrayList<>();
        String queryString = "" +
                "SELECT b.b_id, b.title, b.text, a.a_id, a.name " +
                "FROM book b " +
                "JOIN author_book ab ON b.b_id = ab.b_id " +
                "JOIN author a ON ab.a_id = a.a_id " +
                "JOIN book_location bl ON b.b_id = bl.b_id " +
                "JOIN location l ON bl.l_id = l.l_id " +
                "WHERE l.name = ?;";


        Connection con = null;
        try {
            con = connector.getConnection();
            PreparedStatement statement = con.prepareStatement(queryString);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        resultSet.getString(3)
                );
                book.addAuthor(new Author(
                        resultSet.getLong(4),
                        resultSet.getString(5)
                ));
                books.add(book);
            }
        } catch (SQLException | ClassNotFoundException e) {
            if (Objects.equals(System.getenv("PROCESS_ENV"), "prod")) {
                return null;
            } else {
                e.printStackTrace();
            }
        } finally {
            connector.closeConnection();
        }
        return books;
    }
}
