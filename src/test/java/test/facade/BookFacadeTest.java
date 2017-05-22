package test.facade;

import main.dao.BookDAOMongo;
import main.dao.BookDAOMySQL;
import main.dao.IBookDAO;
import main.dto.Author;
import main.dto.Book;
import main.dto.Location;
import main.exception.BookNotFoundException;
import main.facade.BookFacade;
import main.facade.IBookFacade;
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
public class BookFacadeTest {
    @Test
    public void mySQLDependencyInjectedConstructorTest() {
        IBookDAO dao = new BookDAOMySQL();
        IBookFacade facade = new BookFacade(dao);
        assertThat(facade, is(notNullValue()));
    }

    @Test
    public void MongoDependencyInjectedConstructorTest() {
        IBookDAO dao = new BookDAOMongo();
        IBookFacade facade = new BookFacade(dao);
        assertThat(facade, is(notNullValue()));
    }

    @Test
    public void getMySQLSuccessfulBooksFromLatitudeLongitudeTest() throws BookNotFoundException {
        IBookFacade facade;
        IBookDAO dao;

        List<Book> books = new ArrayList<>();
        books.add(new Book());

        dao = mock(BookDAOMySQL.class);
        when(dao.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt())).
                thenReturn(books);

        facade = new BookFacade(dao);
        assertThat(facade.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt()), is(books));
    }

    @Test(expected = BookNotFoundException.class)
    public void getMySQLEmptyResponseBooksFromLatitudeLongitudeTest() throws BookNotFoundException {
        IBookFacade facade;
        IBookDAO dao;

        dao = mock(BookDAOMySQL.class);
        when(dao.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt())).
                thenReturn(null);

        facade = new BookFacade(dao);
        facade.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt());
    }

    @Test
    public void getMongoSuccessfulBooksFromLatitudeLongitudeTest() throws BookNotFoundException {
        IBookFacade facade;
        IBookDAO dao;

        List<Book> books = new ArrayList<>();
        books.add(new Book());

        dao = mock(BookDAOMongo.class);
        when(dao.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt())).
                thenReturn(books);

        facade = new BookFacade(dao);
        assertThat(facade.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt()), is(books));
    }

    @Test(expected = BookNotFoundException.class)
    public void getMongoEmptyResponseBooksFromLatitudeLongitudeTest() throws BookNotFoundException {
        IBookFacade facade;
        IBookDAO dao;

        dao = mock(BookDAOMongo.class);
        when(dao.getBooksFromLatLong(anyDouble(),anyDouble(), anyInt())).
                thenReturn(null);

        facade = new BookFacade(dao);
        facade.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt());
    }

    @Test
    public void getMySQLSuccessfulBooksAndCitiesFromAuthorTest() throws BookNotFoundException {
        IBookFacade facade;
        IBookDAO dao;

        List<Location> locations = new ArrayList<>();
        locations.add(new Location(1L,1.1231, 1.12312, "Jydeland"));
        List<Author> authors = new ArrayList<>();

        List<Book> books = new ArrayList<>();
        books.add(new Book("title", authors, locations, "text"));

        dao = mock(BookDAOMySQL.class);
        when(dao.getBooksAndCitiesFromAuthor(anyString())).
                thenReturn(books);

        facade = new BookFacade(dao);
        assertThat(facade.getBooksAndCitiesFromAuthor(anyString()), is(books));
    }

    @Test(expected = BookNotFoundException.class)
    public void getMySQLEmptyResponseBooksAndCitiesFromAuthorTest() throws BookNotFoundException {
        IBookFacade facade;
        IBookDAO dao;

        Author author = new Author();
        dao = mock(BookDAOMySQL.class);
        when(dao.getBooksAndCitiesFromAuthor(anyString()))
                .thenReturn(null);

        facade = new BookFacade(dao);
        facade.getBooksAndCitiesFromAuthor(anyString());
    }

    @Test
    public void getMongoSuccessfulBooksAndCitiesFromAuthorTest() throws BookNotFoundException {
        IBookFacade facade;
        IBookDAO dao;

        List<Location> locations = new ArrayList<>();
        locations.add(new Location(1L, 1.1231, 1.12312, "Jydeland"));
        List<Author> authors = new ArrayList<>();

        List<Book> books = new ArrayList<>();
        books.add(new Book("title", authors, locations, "text"));

        Author author = new Author();
        dao = mock(BookDAOMongo.class);
        when(dao.getBooksAndCitiesFromAuthor(anyString())).
                thenReturn(books);

        facade = new BookFacade(dao);
        assertThat(facade.getBooksAndCitiesFromAuthor(anyString()), is(books));
    }

    @Test(expected = BookNotFoundException.class)
    public void getMongoEmptyResponseBooksAndCitiesFromAuthorTest() throws BookNotFoundException {
        IBookFacade facade;
        IBookDAO dao;

        Author author = new Author();
        dao = mock(BookDAOMongo.class);
        when(dao.getBooksAndCitiesFromAuthor(anyString())).
                thenReturn(null);

        facade = new BookFacade(dao);
        facade.getBooksAndCitiesFromAuthor(anyString());
    }

    @Test
    public void getMySQLSuccessfulCitiesFromBookTest() throws BookNotFoundException {
        IBookFacade facade;
        IBookDAO dao;

        List<Location> locations = new ArrayList<>();
        locations.add(new Location(1L, 1.1231, 1.12312, "Jydeland"));

        dao = mock(BookDAOMySQL.class);
        when(dao.getCitiesFromBook("title"))
                .thenReturn(locations);

        facade = new BookFacade(dao);
        assertThat(facade.getCitiesFromBook("title"), is(locations));
    }

    @Test(expected = BookNotFoundException.class)
    public void getMySQLEmptyResponseCitiesFromBookTest() throws BookNotFoundException {
        IBookFacade facade;
        IBookDAO dao;

        dao = mock(BookDAOMySQL.class);
        when(dao.getCitiesFromBook("title")).
                thenReturn(null);

        facade = new BookFacade(dao);
        facade.getCitiesFromBook("title");
    }

    @Test
    public void getMongoSuccessfulCitiesFromBookTest() throws BookNotFoundException {
        IBookFacade facade;
        IBookDAO dao;

        List<Location> locations = new ArrayList<>();
        locations.add(new Location(1L, 1.1231, 1.12312, "Jydeland"));

        dao = mock(BookDAOMongo.class);
        when(dao.getCitiesFromBook("title"))
                .thenReturn(locations);

        facade = new BookFacade(dao);
        assertThat(facade.getCitiesFromBook("title"), is(locations));
    }

    @Test(expected = BookNotFoundException.class)
    public void getMongoEmptyResponseCitiesFromBookTest() throws BookNotFoundException {
        IBookFacade facade;
        IBookDAO dao;

        dao = mock(BookDAOMongo.class);
        when(dao.getCitiesFromBook("title")).
                thenReturn(null);

        facade = new BookFacade(dao);
        facade.getCitiesFromBook("title");
    }

    @Test
    public void getMySQLSuccessfulAuthorsAndBooksFromCity() throws BookNotFoundException {
        IBookFacade facade;
        IBookDAO dao;

        List<Location> locations = new ArrayList<>();
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("Hans"));

        List<Book> books = new ArrayList<>();
        books.add(new Book("title", authors, locations, "text"));

        dao = mock(BookDAOMySQL.class);
        when(dao.getAuthorsAndBooksFromCity(anyString())).
                thenReturn(books);

        facade = new BookFacade(dao);
        assertThat(facade.getAuthorsAndBookFromCity(anyString()), is(books));
    }

    @Test(expected = BookNotFoundException.class)
    public void getMySQLEmptyResponseAuthorsAndBooksFromCity() throws BookNotFoundException {
        IBookFacade facade;
        IBookDAO dao;

        Location location = new Location();
        dao = mock(BookDAOMySQL.class);
        when(dao.getAuthorsAndBooksFromCity(anyString())).
                thenReturn(null);

        facade = new BookFacade(dao);
        facade.getAuthorsAndBookFromCity(anyString());
    }

    @Test
    public void getMongoSuccessfulAuthorsAndBooksFromCity() throws BookNotFoundException {
        IBookFacade facade;
        IBookDAO dao;

        List<Location> locations = new ArrayList<>();
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("Hans"));

        List<Book> books = new ArrayList<>();
        books.add(new Book("title", authors, locations, "text"));

        Location location = new Location();
        dao = mock(BookDAOMongo.class);
        when(dao.getAuthorsAndBooksFromCity(anyString())).
                thenReturn(books);

        facade = new BookFacade(dao);
        assertThat(facade.getAuthorsAndBookFromCity(anyString()), is(books));
    }

    @Test(expected = BookNotFoundException.class)
    public void getMongoEmptyResponseAuthorsAndBooksFromCity() throws BookNotFoundException {
        IBookFacade facade;
        IBookDAO dao;

        Location location = new Location();
        dao = mock(BookDAOMongo.class);
        when(dao.getAuthorsAndBooksFromCity(anyString())).
                thenReturn(null);

        facade = new BookFacade(dao);
        facade.getAuthorsAndBookFromCity(anyString());
    }
}
