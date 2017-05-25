package test.api;

import main.api.MongoAPI;
import main.dto.Book;
import main.dto.Location;
import main.exception.BookNotFoundException;
import main.exception.LocationNotFoundException;
import main.facade.BookFacadeMongo;
import main.facade.IBookFacadeMongo;
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

public class MongoAPITest {

    @Test
    public void defaultConstructorTest() {
        MongoAPI api = new MongoAPI();

        assertThat(api, is(notNullValue()));
    }

    @Test
    public void dependencyInjectorTest() {
        IBookFacadeMongo facade = new BookFacadeMongo();
        MongoAPI api = new MongoAPI(facade);

        assertThat(api, is(notNullValue()));
    }


    @Test
    public void getRootTest() {
        MongoAPI api = new MongoAPI();

        Response res = api.getRoot();

        assertThat(res.getStatus(), is(200));
    }

    @Test
    public void successfulGetBooksFromLatLongTest() {
        MongoAPI api;
        IBookFacadeMongo facade;
        List<Book> books = new ArrayList<Book>() {{
            add(new Book());
        }};

        facade = mock(BookFacadeMongo.class);
        when(facade.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt(), anyInt()))
                .thenReturn(books);

        api = new MongoAPI(facade);
        Response response = api.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt(), anyInt());

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void unsuccessfulGetBooksFromLatLongTest() {
        MongoAPI api;
        IBookFacadeMongo facade;

        facade = mock(BookFacadeMongo.class);
        when(facade.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt(), anyInt()))
                .thenThrow(new BookNotFoundException("msg"));

        api = new MongoAPI(facade);
        Response response = api.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt(), anyInt());

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void successfulGetBooksAndCitiesFromAuthorTest() {
        MongoAPI api;
        IBookFacadeMongo facade;
        List<Book> books = new ArrayList<Book>() {{
            add(new Book());
        }};

        facade = mock(BookFacadeMongo.class);
        when(facade.getBooksAndCitiesFromAuthor(anyString(), anyInt()))
                .thenReturn(books);

        api = new MongoAPI(facade);
        Response response = api.booksAndCitiesFromAuthor(anyString(), anyInt());

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void unsuccessfulGetBooksAndCitiesFromAuthorTest() {
        MongoAPI api;
        IBookFacadeMongo facade;

        facade = mock(BookFacadeMongo.class);
        when(facade.getBooksAndCitiesFromAuthor(anyString(), anyInt()))
                .thenThrow(new BookNotFoundException("msg"));

        api = new MongoAPI(facade);
        Response response = api.booksAndCitiesFromAuthor(anyString(), anyInt());

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void successfulGetLocationsFromBookTest() {
        MongoAPI api;
        IBookFacadeMongo facade;
        List<Location> locations = new ArrayList<Location>() {{
            add(new Location());
        }};

        facade = mock(BookFacadeMongo.class);
        when(facade.getCitiesFromBook(anyString(), anyInt()))
                .thenReturn(locations);

        api = new MongoAPI(facade);
        Response response = api.getLocationsFromBook(anyString(), anyInt());

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void unsuccessfulGetLocationsFromBookTest() {
        MongoAPI api;
        IBookFacadeMongo facade;

        facade = mock(BookFacadeMongo.class);
        when(facade.getCitiesFromBook(anyString(), anyInt()))
                .thenThrow(new LocationNotFoundException("msg"));

        api = new MongoAPI(facade);
        Response response = api.getLocationsFromBook(anyString(), anyInt());

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void successfulGetBooksFromCity() {
        MongoAPI api;
        IBookFacadeMongo facade;
        List<Book> books = new ArrayList<Book>() {{
            add(new Book());
        }};

        facade = mock(BookFacadeMongo.class);
        when(facade.getAuthorsAndBookFromCity(anyString(), anyInt()))
                .thenReturn(books);

        api = new MongoAPI(facade);
        Response response = api.getBooksFromCity(anyString(), anyInt());

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void unsuccessfulGetBooksFromCity() {
        MongoAPI api;
        IBookFacadeMongo facade;

        facade = mock(BookFacadeMongo.class);
        when(facade.getAuthorsAndBookFromCity(anyString(), anyInt()))
                .thenThrow(new BookNotFoundException("msg"));

        api = new MongoAPI(facade);
        Response response = api.getBooksFromCity(anyString(), anyInt());

        assertThat(response.getStatus(), is(400));
    }
}