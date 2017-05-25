package test.api;

import main.api.MySQLAPI;
import main.dto.Book;
import main.dto.Location;
import main.exception.AuthorNotFoundException;
import main.exception.BookNotFoundException;
import main.exception.LocationNotFoundException;
import main.facade.BookFacadeMySQL;
import main.facade.IBookFacadeMySQL;
import org.junit.Test;

import javax.ws.rs.core.Response;

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

public class MySQLAPITest {

    @Test
    public void constructorTest() {
        MySQLAPI api = new MySQLAPI();

        assertThat(api, is(notNullValue()));
    }

    @Test
    public void dependencyInjectorConstructorTest() {
        IBookFacadeMySQL facade = new BookFacadeMySQL();
        MySQLAPI api = new MySQLAPI(facade);

        assertThat(api, is(notNullValue()));
    }

    @Test
    public void rootEndpointTest() {
        MySQLAPI api = new MySQLAPI();

        Response response = api.getRoot();

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void successfulGetBooksFromLatLongTest() {
        MySQLAPI api;
        BookFacadeMySQL facade;
        List<Book> books = new ArrayList<Book>() {{
            add(new Book());
        }} ;

        facade = mock(BookFacadeMySQL.class);
        when(facade.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt(), anyInt()))
                .thenReturn(books);

        api = new MySQLAPI(facade);
        Response response = api.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt(), anyInt());

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void unsuccessfulGetBooksFromLatLongTest() {
        MySQLAPI api;
        BookFacadeMySQL facade;

        facade = mock(BookFacadeMySQL.class);
        when(facade.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt(), anyInt()))
                .thenThrow(new BookNotFoundException("msg"));

        api = new MySQLAPI(facade);
        Response response = api.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt(), anyInt());

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void successfulBooksAndCitiesFromAuthorTest() {
        MySQLAPI api;
        BookFacadeMySQL facade;
        List<Book> books = new ArrayList<Book>() {{
            add(new Book());
        }};

        facade = mock(BookFacadeMySQL.class);
        when(facade.getBooksAndCitiesFromAuthor(anyString(), anyInt()))
                .thenReturn(books);

        api = new MySQLAPI(facade);
        Response response = api.booksAndCitiesFromAuthor(anyString(), anyInt());

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void unsuccessfulBooksAndCitiesFromAuthorTest() {
        MySQLAPI api;
        BookFacadeMySQL facade;

        facade = mock(BookFacadeMySQL.class);
        when(facade.getBooksAndCitiesFromAuthor(anyString(), anyInt()))
                .thenThrow(BookNotFoundException.class);

        api = new MySQLAPI(facade);
        Response response = api.booksAndCitiesFromAuthor(anyString(), anyInt());

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void successfulGetLocationsFromBookTest() {
        MySQLAPI api;
        BookFacadeMySQL facade;
        List<Location> locations = new ArrayList<Location>() {{
            add(new Location());
        }};

        facade = mock(BookFacadeMySQL.class);
        when(facade.getCitiesFromBook(anyString(), anyInt()))
                .thenReturn(locations);

        api = new MySQLAPI(facade);
        Response response = api.getLocationsFromBook(anyString(), anyInt());

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void unsuccessfulGetLocationsFromBooksTest() {
        MySQLAPI api;
        BookFacadeMySQL facade;

        facade = mock(BookFacadeMySQL.class);
        when(facade.getCitiesFromBook(anyString(), anyInt()))
                .thenThrow(LocationNotFoundException.class);

        api = new MySQLAPI(facade);
        Response response = api.getLocationsFromBook(anyString(), anyInt());

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void successfulGetBooksFromCityTest() {
        MySQLAPI api;
        BookFacadeMySQL facade;
        List<Book> books = new ArrayList<Book>() {{
            add(new Book());
        }};

        facade = mock(BookFacadeMySQL.class);
        when(facade.getAuthorsAndBookFromCity(anyString(), anyInt()))
                .thenReturn(books);

        api = new MySQLAPI(facade);
        Response response = api.getBooksFromCity(anyString(), anyInt());

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void unsuccessfulGetBooksFromCityTest() {
        MySQLAPI api;
        BookFacadeMySQL facade;

        facade = mock(BookFacadeMySQL.class);
        when(facade.getAuthorsAndBookFromCity(anyString(), anyInt()))
                .thenThrow(BookNotFoundException.class);

        api = new MySQLAPI(facade);
        Response response = api.getBooksFromCity(anyString(), anyInt());

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void successfulGetAuthorsTest() {
        MySQLAPI api;
        BookFacadeMySQL facade;
        List<String> authors = new ArrayList<String>() {{
            add("author");
        }};

        facade = mock(BookFacadeMySQL.class);
        when(facade.searchForAuthor(anyString(), anyInt()))
                .thenReturn(authors);

        api = new MySQLAPI(facade);
        Response response = api.getAuthors(anyString(), anyInt());

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void unsuccessfulGetAuthorsTest() {
        MySQLAPI api;
        BookFacadeMySQL facade;

        facade = mock(BookFacadeMySQL.class);
        when(facade.searchForAuthor(anyString(), anyInt()))
                .thenThrow(AuthorNotFoundException.class);

        api = new MySQLAPI(facade);
        Response response = api.getAuthors(anyString(), anyInt());

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void successfulGetCitiesTest() {
        MySQLAPI api;
        BookFacadeMySQL facade;
        List<String> cities = new ArrayList<String>() {{
            add("city");
        }};

        facade = mock(BookFacadeMySQL.class);
        when(facade.searchForCity(anyString(), anyInt()))
                .thenReturn(cities);

        api = new MySQLAPI(facade);
        Response response = api.getCities(anyString(), anyInt());

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void unsuccessfulGetCitiesTest() {
        MySQLAPI api;
        BookFacadeMySQL facade;

        facade = mock(BookFacadeMySQL.class);
        when(facade.searchForCity(anyString(), anyInt()))
                .thenThrow(LocationNotFoundException.class);

        api = new MySQLAPI(facade);
        Response response = api.getCities(anyString(), anyInt());

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void successfulGetBooksTest() {
        MySQLAPI api;
        BookFacadeMySQL facade;
        List<String> books = new ArrayList<String>() {{
            add("book");
        }};

        facade = mock(BookFacadeMySQL.class);
        when(facade.searchForBook(anyString(), anyInt()))
                .thenReturn(books);

        api = new MySQLAPI(facade);
        Response response = api.getBooks(anyString(), anyInt());

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void unsuccessfulGetBooksTest() {
        MySQLAPI api;
        BookFacadeMySQL facade;

        facade = mock(BookFacadeMySQL.class);
        when(facade.searchForBook(anyString(), anyInt()))
                .thenThrow(BookNotFoundException.class);

        api = new MySQLAPI(facade);
        Response response = api.getBooks(anyString(), anyInt());

        assertThat(response.getStatus(), is(400));
    }
}