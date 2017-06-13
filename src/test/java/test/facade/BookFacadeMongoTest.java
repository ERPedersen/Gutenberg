package test.facade;

import main.dao.BookDAOMongo;
import main.dao.IBookDAOMongo;
import main.dto.Author;
import main.dto.Book;
import main.dto.Location;
import main.exception.BookNotFoundException;
import main.exception.LocationNotFoundException;
import main.facade.BookFacadeMongo;
import main.facade.IBookFacadeMongo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookFacadeMongoTest {

    @Test
    public void defaultConstructorTest() {
        IBookFacadeMongo facade = new BookFacadeMongo();
        assertThat(facade, is(notNullValue()));
    }

    @Test
    public void dependencyInjectedConstructorTest() {
        IBookDAOMongo dao = new BookDAOMongo();
        IBookFacadeMongo facade = new BookFacadeMongo(dao);
        assertThat(facade, is(notNullValue()));
    }

    @Test
    public void getSuccessfulBooksFromLatitudeLongitudeTest() {
        IBookFacadeMongo facade;
        IBookDAOMongo dao;

        List<Book> books = new ArrayList<>();
        books.add(new Book());

        dao = mock(BookDAOMongo.class);
        when(dao.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt(), anyInt())).
                thenReturn(books);

        facade = new BookFacadeMongo(dao);
        assertThat(facade.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt(), anyInt()), is(books));
    }

    @Test
    public void examassertion() {
        assertThat(1, is(1));
    }

    @Test(expected = BookNotFoundException.class)
    public void getEmptyResponseBooksFromLatitudeLongitudeTest() {
        IBookFacadeMongo facade;
        IBookDAOMongo dao;

        dao = mock(BookDAOMongo.class);
        when(dao.getBooksFromLatLong(anyDouble(),anyDouble(), anyInt(), anyInt())).
                thenReturn(null);

        facade = new BookFacadeMongo(dao);
        facade.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt(), anyInt());
    }

    @Test
    public void getSuccessfulBooksAndCitiesFromAuthorTest() {
        IBookFacadeMongo facade;
        IBookDAOMongo dao;

        List<Location> locations = new ArrayList<>();
        locations.add(new Location(1L, 1.1231, 1.12312, "Jydeland"));
        List<Author> authors = new ArrayList<>();

        List<Book> books = new ArrayList<>();
        books.add(new Book("title", authors, locations, "text"));

        dao = mock(BookDAOMongo.class);
        when(dao.getBooksAndCitiesFromAuthor(anyString(), anyInt())).
                thenReturn(books);

        facade = new BookFacadeMongo(dao);
        assertThat(facade.getBooksAndCitiesFromAuthor(anyString(), anyInt()), is(books));
    }

    @Test(expected = BookNotFoundException.class)
    public void getEmptyResponseBooksAndCitiesFromAuthorTest() {
        IBookFacadeMongo facade;
        IBookDAOMongo dao;

        dao = mock(BookDAOMongo.class);
        when(dao.getBooksAndCitiesFromAuthor(anyString(), anyInt())).
                thenReturn(null);

        facade = new BookFacadeMongo(dao);
        facade.getBooksAndCitiesFromAuthor(anyString(), anyInt());
    }

    @Test
    public void getSuccessfulCitiesFromBookTest() {
        IBookFacadeMongo facade;
        IBookDAOMongo dao;

        List<Location> locations = new ArrayList<>();
        locations.add(new Location(1L, 1.1231, 1.12312, "Jydeland"));

        dao = mock(BookDAOMongo.class);
        when(dao.getCitiesFromBook(anyString(), anyInt()))
                .thenReturn(locations);

        facade = new BookFacadeMongo(dao);
        assertThat(facade.getCitiesFromBook(anyString(), anyInt()), is(locations));
    }

    @Test(expected = LocationNotFoundException.class)
    public void getEmptyResponseCitiesFromBookTest() {
        IBookFacadeMongo facade;
        IBookDAOMongo dao;

        dao = mock(BookDAOMongo.class);
        when(dao.getCitiesFromBook(anyString(), anyInt())).
                thenReturn(null);

        facade = new BookFacadeMongo(dao);
        facade.getCitiesFromBook(anyString(), anyInt());
    }

    @Test
    public void getSuccessfulAuthorsAndBooksFromCity() {
        IBookFacadeMongo facade;
        IBookDAOMongo dao;

        List<Location> locations = new ArrayList<>();
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("Hans"));

        List<Book> books = new ArrayList<>();
        books.add(new Book("title", authors, locations, "text"));

        dao = mock(BookDAOMongo.class);
        when(dao.getAuthorsAndBooksFromCity(anyString(), anyInt())).
                thenReturn(books);

        facade = new BookFacadeMongo(dao);
        assertThat(facade.getAuthorsAndBookFromCity(anyString(), anyInt()), is(books));
    }

    @Test(expected = BookNotFoundException.class)
    public void getEmptyResponseAuthorsAndBooksFromCity() {
        IBookFacadeMongo facade;
        IBookDAOMongo dao;

        dao = mock(BookDAOMongo.class);
        when(dao.getAuthorsAndBooksFromCity(anyString(), anyInt())).
                thenReturn(null);

        facade = new BookFacadeMongo(dao);
        facade.getAuthorsAndBookFromCity(anyString(), anyInt());
    }
}
