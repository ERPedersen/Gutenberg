package test.facade;

import main.dao.BookDAOMySQL;
import main.dao.IBookDAOMySQL;
import main.dto.Author;
import main.dto.Book;
import main.dto.Location;
import main.exception.AuthorNotFoundException;
import main.exception.BookNotFoundException;
import main.exception.LocationNotFoundException;
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
    public void getMySQLSuccessfulBooksFromLatitudeLongitudeTest() {
        IBookFacadeMySQL facade;
        IBookDAOMySQL dao;

        List<Book> books = new ArrayList<>();
        books.add(new Book());

        dao = mock(BookDAOMySQL.class);
        when(dao.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt(), anyInt())).
                thenReturn(books);

        facade = new BookFacadeMySQL(dao);
        assertThat(facade.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt(), anyInt()), is(books));
    }

    @Test(expected = BookNotFoundException.class)
    public void getMySQLEmptyResponseBooksFromLatitudeLongitudeTest() {
        IBookFacadeMySQL facade;
        IBookDAOMySQL dao;

        dao = mock(BookDAOMySQL.class);
        when(dao.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt(), anyInt())).
                thenReturn(null);

        facade = new BookFacadeMySQL(dao);
        facade.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt(), anyInt());
    }

    @Test
    public void getMySQLSuccessfulBooksAndCitiesFromAuthorTest() {
        IBookFacadeMySQL facade;
        IBookDAOMySQL dao;

        List<Location> locations = new ArrayList<>();
        locations.add(new Location(1L,1.1231, 1.12312, "Jydeland"));
        List<Author> authors = new ArrayList<>();

        List<Book> books = new ArrayList<>();
        books.add(new Book("title", authors, locations, "text"));

        dao = mock(BookDAOMySQL.class);
        when(dao.getBooksAndCitiesFromAuthor(anyString(), anyInt())).
                thenReturn(books);

        facade = new BookFacadeMySQL(dao);
        assertThat(facade.getBooksAndCitiesFromAuthor(anyString(), anyInt()), is(books));
    }

    @Test(expected = BookNotFoundException.class)
    public void getMySQLEmptyResponseBooksAndCitiesFromAuthorTest() {
        IBookFacadeMySQL facade;
        IBookDAOMySQL dao;

        Author author = new Author();
        dao = mock(BookDAOMySQL.class);
        when(dao.getBooksAndCitiesFromAuthor(anyString(), anyInt()))
                .thenReturn(null);

        facade = new BookFacadeMySQL(dao);
        facade.getBooksAndCitiesFromAuthor(anyString(), anyInt());
    }

    @Test
    public void getMySQLSuccessfulCitiesFromBookTest() {
        IBookFacadeMySQL facade;
        IBookDAOMySQL dao;

        List<Location> locations = new ArrayList<>();
        locations.add(new Location(1L, 1.1231, 1.12312, "Jydeland"));

        dao = mock(BookDAOMySQL.class);
        when(dao.getCitiesFromBook("title", 10))
                .thenReturn(locations);

        facade = new BookFacadeMySQL(dao);
        assertThat(facade.getCitiesFromBook("title", 10), is(locations));
    }

    @Test(expected = LocationNotFoundException.class)
    public void getMySQLEmptyResponseCitiesFromBookTest() {
        IBookFacadeMySQL facade;
        IBookDAOMySQL dao;

        dao = mock(BookDAOMySQL.class);
        when(dao.getCitiesFromBook("title", 10)).
                thenReturn(null);

        facade = new BookFacadeMySQL(dao);
        facade.getCitiesFromBook("title", 10);
    }

    @Test
    public void getMySQLSuccessfulAuthorsAndBooksFromCity() {
        IBookFacadeMySQL facade;
        IBookDAOMySQL dao;

        List<Location> locations = new ArrayList<>();
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("Hans"));

        List<Book> books = new ArrayList<>();
        books.add(new Book("title", authors, locations, "text"));

        dao = mock(BookDAOMySQL.class);
        when(dao.getAuthorsAndBooksFromCity(anyString(), anyInt())).
                thenReturn(books);

        facade = new BookFacadeMySQL(dao);
        assertThat(facade.getAuthorsAndBookFromCity(anyString(), anyInt()), is(books));
    }

    @Test(expected = BookNotFoundException.class)
    public void getMySQLEmptyResponseAuthorsAndBooksFromCity() {
        IBookFacadeMySQL facade;
        IBookDAOMySQL dao;

        Location location = new Location();
        dao = mock(BookDAOMySQL.class);
        when(dao.getAuthorsAndBooksFromCity(anyString(), anyInt())).
                thenReturn(null);

        facade = new BookFacadeMySQL(dao);
        facade.getAuthorsAndBookFromCity(anyString(), anyInt());
    }

    @Test
    public void successfulSearchForCityTest() {
        IBookFacadeMySQL facade;
        IBookDAOMySQL dao;

        List<String> cities = new ArrayList<String>(){{
            add("test");
        }};
        dao = mock(BookDAOMySQL.class);
        when(dao.searchForCity(anyString(), anyInt()))
                .thenReturn(cities);

        facade = new BookFacadeMySQL(dao);
        assertThat(facade.searchForCity(anyString(), anyInt()), is(cities));
    }

    @Test(expected = LocationNotFoundException.class)
    public void unsuccessfulSearchForCityTest() {
        IBookFacadeMySQL facade;
        IBookDAOMySQL dao;
        dao = mock(BookDAOMySQL.class);
        when(dao.searchForCity(anyString(), anyInt()))
                .thenReturn(null);

        facade = new BookFacadeMySQL(dao);
        facade.searchForCity(anyString(), anyInt());
    }

    @Test
    public void successfulSearchForBookTest() {
        IBookFacadeMySQL facade;
        IBookDAOMySQL dao;
        List<String> books = new ArrayList<String>() {{
            add("test");
        }};
        dao = mock(BookDAOMySQL.class);
        when(dao.searchForBook(anyString(), anyInt()))
                .thenReturn(books);

        facade = new BookFacadeMySQL(dao);
        facade.searchForBook(anyString(), anyInt());
    }

    @Test(expected = BookNotFoundException.class)
    public void unsuccessfulSearchForBookTest() {
        IBookFacadeMySQL facade;
        IBookDAOMySQL dao;

        dao = mock(BookDAOMySQL.class);
        when(dao.searchForBook(anyString(), anyInt()))
                .thenReturn(null);

        facade = new BookFacadeMySQL(dao);
        facade.searchForBook(anyString(), anyInt());
    }

    @Test
    public void successfulSearchForAuthorTest() {
        IBookFacadeMySQL facade;
        IBookDAOMySQL dao;
        List<String> authors = new ArrayList<String>() {{
            add("test");
        }};
        dao = mock(BookDAOMySQL.class);
        when(dao.searchForAuthor(anyString(), anyInt()))
                .thenReturn(authors);

        facade = new BookFacadeMySQL(dao);
        assertThat(facade.searchForAuthor(anyString(), anyInt()), is(authors));
    }

    @Test(expected = AuthorNotFoundException.class)
    public void unsuccessfulSearchForAuthorTest() {
        IBookFacadeMySQL facade;
        IBookDAOMySQL dao;

        dao = mock(BookDAOMySQL.class);
        when(dao.searchForAuthor(anyString(), anyInt()))
                .thenReturn(null);
        facade = new BookFacadeMySQL(dao);
        facade.searchForAuthor(anyString(), anyInt());
    }
}
