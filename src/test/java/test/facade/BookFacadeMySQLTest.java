package test.facade;

import main.dao.BookDAOMySQL;
import main.dao.IBookDAOMySQL;
import main.dto.Author;
import main.dto.Book;
import main.dto.Location;
import main.exception.BookNotFoundException;
import main.facade.BookFacadeMySQL;
import main.facade.IBookFacadeMySQL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

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

@RunWith(MockitoJUnitRunner.class)
public class BookFacadeMySQLTest {

    @Test
    public void defaultConstructorTest() {
        IBookFacadeMySQL facade = new BookFacadeMySQL();
        assertThat(facade, is(notNullValue()));
    }

    @Test
    public void mySQLDependencyInjectedConstructorTest() {
        IBookDAOMySQL dao = new BookDAOMySQL();
        IBookFacadeMySQL facade = new BookFacadeMySQL(dao);
        assertThat(facade, is(notNullValue()));
    }

    @Test
    public void getMySQLSuccessfulBooksFromLatitudeLongitudeTest() throws BookNotFoundException {
        IBookFacadeMySQL facade;
        IBookDAOMySQL dao;

        List<Book> books = new ArrayList<>();
        books.add(new Book());

        dao = mock(BookDAOMySQL.class);
        when(dao.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt())).
                thenReturn(books);

        facade = new BookFacadeMySQL(dao);
        assertThat(facade.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt()), is(books));
    }

    @Test(expected = BookNotFoundException.class)
    public void getMySQLEmptyResponseBooksFromLatitudeLongitudeTest() throws BookNotFoundException {
        IBookFacadeMySQL facade;
        IBookDAOMySQL dao;

        dao = mock(BookDAOMySQL.class);
        when(dao.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt())).
                thenReturn(null);

        facade = new BookFacadeMySQL(dao);
        facade.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt());
    }

    @Test
    public void getMySQLSuccessfulBooksAndCitiesFromAuthorTest() throws BookNotFoundException {
        IBookFacadeMySQL facade;
        IBookDAOMySQL dao;

        List<Location> locations = new ArrayList<>();
        locations.add(new Location(1L,1.1231, 1.12312, "Jydeland"));
        List<Author> authors = new ArrayList<>();

        List<Book> books = new ArrayList<>();
        books.add(new Book("title", authors, locations, "text"));

        dao = mock(BookDAOMySQL.class);
        when(dao.getBooksAndCitiesFromAuthor(anyString())).
                thenReturn(books);

        facade = new BookFacadeMySQL(dao);
        assertThat(facade.getBooksAndCitiesFromAuthor(anyString()), is(books));
    }

    @Test(expected = BookNotFoundException.class)
    public void getMySQLEmptyResponseBooksAndCitiesFromAuthorTest() throws BookNotFoundException {
        IBookFacadeMySQL facade;
        IBookDAOMySQL dao;

        Author author = new Author();
        dao = mock(BookDAOMySQL.class);
        when(dao.getBooksAndCitiesFromAuthor(anyString()))
                .thenReturn(null);

        facade = new BookFacadeMySQL(dao);
        facade.getBooksAndCitiesFromAuthor(anyString());
    }

    @Test
    public void getMySQLSuccessfulCitiesFromBookTest() throws BookNotFoundException {
        IBookFacadeMySQL facade;
        IBookDAOMySQL dao;

        List<Location> locations = new ArrayList<>();
        locations.add(new Location(1L, 1.1231, 1.12312, "Jydeland"));

        dao = mock(BookDAOMySQL.class);
        when(dao.getCitiesFromBook("title"))
                .thenReturn(locations);

        facade = new BookFacadeMySQL(dao);
        assertThat(facade.getCitiesFromBook("title"), is(locations));
    }

    @Test(expected = BookNotFoundException.class)
    public void getMySQLEmptyResponseCitiesFromBookTest() throws BookNotFoundException {
        IBookFacadeMySQL facade;
        IBookDAOMySQL dao;

        dao = mock(BookDAOMySQL.class);
        when(dao.getCitiesFromBook("title")).
                thenReturn(null);

        facade = new BookFacadeMySQL(dao);
        facade.getCitiesFromBook("title");
    }

    @Test
    public void getMySQLSuccessfulAuthorsAndBooksFromCity() throws BookNotFoundException {
        IBookFacadeMySQL facade;
        IBookDAOMySQL dao;

        List<Location> locations = new ArrayList<>();
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("Hans"));

        List<Book> books = new ArrayList<>();
        books.add(new Book("title", authors, locations, "text"));

        dao = mock(BookDAOMySQL.class);
        when(dao.getAuthorsAndBooksFromCity(anyString())).
                thenReturn(books);

        facade = new BookFacadeMySQL(dao);
        assertThat(facade.getAuthorsAndBookFromCity(anyString()), is(books));
    }

    @Test(expected = BookNotFoundException.class)
    public void getMySQLEmptyResponseAuthorsAndBooksFromCity() throws BookNotFoundException {
        IBookFacadeMySQL facade;
        IBookDAOMySQL dao;

        Location location = new Location();
        dao = mock(BookDAOMySQL.class);
        when(dao.getAuthorsAndBooksFromCity(anyString())).
                thenReturn(null);

        facade = new BookFacadeMySQL(dao);
        facade.getAuthorsAndBookFromCity(anyString());
    }
}
