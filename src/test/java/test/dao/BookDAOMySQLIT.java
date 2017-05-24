package test.dao;

import main.dao.BookDAOMySQL;
import main.dto.Book;
import main.dto.Location;
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
public class BookDAOMySQLIT {

    BookDAOMySQL dao;

    @Test
    public void defaultConstructorTest() {
        dao = new BookDAOMySQL();

        assertThat(dao, is(notNullValue()));
    }

    @Test
    public void dependencyConstructorTest() {
        dao = new BookDAOMySQL();

        assertThat(dao, is(notNullValue()));
    }

    @Test
    public void successfulGetCitiesFromBookTest() {
        dao = new BookDAOMySQL();

        List<Location> cities = dao.getCitiesFromBook("What Peace Means", 10);

        assertThat(cities, hasSize(greaterThan(0)));

    }

    @Test
    public void unsuccessfulGetCitiesFromBookTest() {
        dao = new BookDAOMySQL();

        List<Location> cities = dao.getCitiesFromBook("BoogerButt Anthology, Book III", 10);

        assertThat(cities, hasSize(equalTo(0)));

    }

    @Test
    public void successfulGetAuthorsAndBooksFromCity() {
        dao = new BookDAOMySQL();

        List<Book> books = dao.getAuthorsAndBooksFromCity("Washington", 10);

        assertThat(books, hasSize(greaterThan(0)));
    }

    @Test
    public void unsuccessfulGetAuthorsAndBooksFromCity() {
        dao = new BookDAOMySQL();

        List<Book> books = dao.getAuthorsAndBooksFromCity("New Donk City", 10);

        assertThat(books, hasSize(equalTo(0)));
    }

    @Test
    public void successfulGetBooksAndCitiesFromAuthor() {
        dao = new BookDAOMySQL();

        List<Book> books = dao.getBooksAndCitiesFromAuthor("Thomas Clarkson", 10);

        assertThat(books, hasSize(greaterThan(0)));
    }

    @Test
    public void unsuccessfulGetBooksAndCitiesFromAuthor() {
        dao = new BookDAOMySQL();

        List<Book> books = dao.getBooksAndCitiesFromAuthor("Slab PlunkChunk", 10);

        assertThat(books, hasSize(equalTo(0)));
    }

    @Test
    public void successfulGetBooksFromLatLong() {
        dao = new BookDAOMySQL();

        List<Book> books = dao.getBooksFromLatLong(52.18935,-2.22001,50, 10);

        assertThat(books, hasSize(greaterThan(0)));
    }

    @Test
    public void unsuccessfulGetBooksFromLatLong() {
        dao = new BookDAOMySQL();

        List<Book> books = dao.getBooksFromLatLong(420420.0,-696969.0,666, 10);

        assertThat(books, hasSize(equalTo(0)));
    }

}
