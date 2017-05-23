package test.dao;

import main.dao.BookDAOMongo;
import main.dto.Book;
import main.dto.Location;
import main.util.DBConnectorMongo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(MockitoJUnitRunner.class)
public class BookDAOMongoIT {

    BookDAOMongo dao;

    @Test
    public void defaultConstructorTest() {
        dao = new BookDAOMongo();

        assertThat(dao, is(notNullValue()));
    }

    @Test
    public void dependencyConstructorTest() {
        DBConnectorMongo connector = new DBConnectorMongo();
        dao = new BookDAOMongo(connector);

        assertThat(dao, is(notNullValue()));
    }

    @Test
    public void successfulGetCitiesFromBookTest() {
        dao = new BookDAOMongo();

        List<Location> cities = dao.getCitiesFromBook("The Truce of God",100);

        assertThat(cities, hasSize(greaterThan(0)));

    }

    @Test
    public void unsuccessfulGetCitiesFromBookTest() {
        dao = new BookDAOMongo();

        List<Location> cities = dao.getCitiesFromBook("BoogerButt Anthology, Book III",100);

        assertThat(cities, hasSize(equalTo(0)));

    }

    @Test
    public void successfulGetAuthorsAndBooksFromCity() {
        dao = new BookDAOMongo();

        List<Book> books = dao.getAuthorsAndBooksFromCity("Washington",100);

        assertThat(books, hasSize(greaterThan(0)));
    }

    @Test
    public void unsuccessfulGetAuthorsAndBooksFromCity() {
        dao = new BookDAOMongo();

        List<Book> books = dao.getAuthorsAndBooksFromCity("New Donk City",100);

        assertThat(books, hasSize(equalTo(0)));
    }

    @Test
    public void successfulGetBooksAndCitiesFromAuthor() {
        dao = new BookDAOMongo();

        List<Book> books = dao.getBooksAndCitiesFromAuthor("Thomas Clarkson",100);

        assertThat(books, hasSize(greaterThan(0)));
    }

    @Test
    public void unsuccessfulGetBooksAndCitiesFromAuthor() {
        dao = new BookDAOMongo();

        List<Book> books = dao.getBooksAndCitiesFromAuthor("Slab PlunkChunk",100);

        assertThat(books, hasSize(equalTo(0)));
    }

    @Test
    public void successfulGetBooksFromLatLong() {
        dao = new BookDAOMongo();

        List<Book> books = dao.getBooksFromLatLong(53.79391,-1.75206,5000,100);

        assertThat(books, hasSize(greaterThan(0)));
    }

    @Test
    public void unsuccessfulGetBooksFromLatLong() {
        dao = new BookDAOMongo();

        List<Book> books = dao.getBooksFromLatLong(0.0,-0.0,1,100);

        assertThat(books, hasSize(equalTo(0)));
    }

}
