package main.dao;

import main.dto.Author;
import main.dto.Book;
import main.dto.Location;
import main.exception.BookNotFoundException;
import main.util.DBConnectorMySQL;
import main.util.IDBConnectorMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookDAOMySQL implements IBookDAOMySQL {

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
     * @param connector The connector to the MYSQL database.
     */
    public BookDAOMySQL(IDBConnectorMySQL connector) {
        this.connector = connector;
    }

    /**
     * Returns a List of books from the MYSQL database which have the location of a latitude and longitude mentioned.
     *
     * @param latitude Double the latitude of the location.
     * @param longitude Double the longitude of the location.
     * @param radius The radius the locations are found from.
     * @param limit The limit of returned rows.
     * @return The list of books where the location is mentioned.
     */
    @Override
    public List<Book> getBooksFromLatLong(double latitude, double longitude, int radius, int limit) {
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
                ")";
                if(limit != 0) {
                    queryString += " LIMIT ?";
                }
                queryString += ";";

        try {
            Connection con = connector.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setDouble(1, latitude);
            preparedStatement.setDouble(2, longitude);
            preparedStatement.setDouble(3, latitude);
            preparedStatement.setInt(4, radius);
            if(limit != 0) {
                preparedStatement.setInt(5, limit);
            }

            ResultSet resultSet = preparedStatement.executeQuery();

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
     * @param name The name of the author who has written the books.
     * @param limit The limit of returned rows.
     * @return Books which are written by the author.
     */
    @Override
    public List<Book> getBooksAndCitiesFromAuthor(String name, int limit) throws BookNotFoundException {
        List<Book> books = new ArrayList<>();
        String queryString =
                "SELECT DISTINCT b.b_id, b.title, b.text, a.a_id, l.l_id, l.latitude, l.longitude, l.name " +
                        "FROM book b " +
                        "JOIN book_location bl ON b.b_id = bl.b_id " +
                        "JOIN location l ON bl.l_id = l.l_id " +
                        "JOIN author_book ab ON b.b_id = ab.b_id " +
                        "JOIN author a ON ab.a_id = a.a_id " +
                        "WHERE a.name = ?" +
                        "ORDER BY b.b_id";
                        if(limit != 0) {
                            queryString += " LIMIT ?";
                        }
                        queryString += ";";
        try {
            Connection con = connector.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setString(1, name);
            if(limit != 0) {
                preparedStatement.setInt(2, limit);
            }
            ResultSet resultSet = preparedStatement.executeQuery();

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
     * @param title The title of the book.
     * @param limit The limit of returned rows.
     * @return Books with locations.
     */
    @Override
    public List<Location> getCitiesFromBook(String title, int limit) {
        List<Location> locations = new ArrayList<>();
        String queryString =
                "SELECT * FROM location l " +
                        "JOIN book_location bl ON l.l_id = bl.l_id " +
                        "JOIN book b ON bl.b_id = b.b_id " +
                        "WHERE MATCH(b.title) AGAINST(?) AND b.title LIKE ?";
                        if(limit != 0) {
                            queryString += " LIMIT ?";
                        }
                        queryString += ";";

        try {
            Connection con = connector.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, title);
            if(limit != 0) {
                preparedStatement.setInt(3, limit);
            }
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
     * @param name The name of the location that is mentioned in the books.
     * @param limit The limit of returned rows.
     * @return The books where the location is mentioned.
     */
    @Override
    public List<Book> getAuthorsAndBooksFromCity(String name, int limit) {
        List<Book> books = new ArrayList<>();
        String queryString = "" +
                "SELECT b.b_id, b.title, b.text, a.a_id, a.name " +
                "FROM book b " +
                "JOIN author_book ab ON b.b_id = ab.b_id " +
                "JOIN author a ON ab.a_id = a.a_id " +
                "JOIN book_location bl ON b.b_id = bl.b_id " +
                "JOIN location l ON bl.l_id = l.l_id " +
                "WHERE l.name = ?";
                if(limit != 0) {
                    queryString += " LIMIT ?";
                }
                queryString += ";";


        Connection con = null;
        try {
            con = connector.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setString(1, name);
            if(limit != 0) {
                preparedStatement.setInt(2, limit);
            }
            ResultSet resultSet = preparedStatement.executeQuery();

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

    /**
     * Returns a list of Author names for fuzzy searching.
     *
     * @param name Partial name of an author.
     * @param limit The limit of returned rows.
     * @return Names of authors.
     */
    @Override
    public List<String> getFuzzySearchAuthor(String name, int limit) {
        List<String> authors = new ArrayList<>();

        String[] split = name.split(" ");

        String starredInput = "";
        for (String spaced : split) {
            starredInput = spaced + "* ";
        }

        starredInput = starredInput.substring(0, starredInput.length()-1);

        String queryString = "" +
                "SELECT name FROM author " +
                "WHERE MATCH(author.name) " +
                "AGAINST(? IN BOOLEAN MODE)";
                if(limit != 0) {
                    queryString += " LIMIT ?";
                }
                queryString += ";";


        Connection con = null;
        try {
            con = connector.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setString(1, starredInput);
            if(limit != 0) {
                preparedStatement.setInt(2,limit);
            }
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {

                authors.add(resultSet.getString(1));

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
        return authors;
    }

    /**
     * Gets a list of Book titles for fuzzy searching.
     *
     * @param title Partial title of a book.
     * @param limit The limit of returned rows.
     * @return Book titles.
     */
    @Override
    public List<String> getFuzzySearchBook(String title, int limit) {
        List<String> books = new ArrayList<>();
        String[] split = title.split(" ");

        String starredInput = "";
        for (String spaced : split) {
            starredInput = spaced + "* ";
        }
        starredInput = starredInput.substring(0, starredInput.length()-1);

        String queryString = "" +
                "SELECT title FROM book " +
                "WHERE MATCH(book.title) " +
                "AGAINST(? IN BOOLEAN MODE)";
                if(limit != 0) {
                    queryString += " LIMIT ?";
                }
                queryString += ";";


        Connection con = null;
        try {
            con = connector.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setString(1, starredInput);
            if(limit != 0) {
                preparedStatement.setInt(2, limit);
            }
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                books.add(resultSet.getString(1));

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
     * Gets a list of City names from a partial name.
     *
     * @param name Partial name of a city.
     * @param limit The limit of returned rows.
     * @return Names of cities.
     */
    @Override
    public List<String> getFuzzySearchCity(String name, int limit)  {
        List<String> cities = new ArrayList<>();
        String[] split = name.split(" ");

        String starredInput = "";
        for (String spaced : split) {
            starredInput = spaced + "* ";
        }
        starredInput = starredInput.substring(0, starredInput.length()-1);

        String queryString = "" +
                "SELECT name FROM location " +
                "WHERE MATCH(location.name) " +
                "AGAINST(? IN BOOLEAN MODE)";
                if(limit != 0) {
                    queryString += " LIMIT ?";
                }
                queryString += ";";


        Connection con;
        try {
            con = connector.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setString(1, starredInput);
            if(limit != 0) {
                preparedStatement.setInt(2, limit);
            }
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                cities.add(resultSet.getString(1));

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
        return cities;
    }
}