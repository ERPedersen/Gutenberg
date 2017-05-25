package test.api;

import main.api.MySQLAPI;
import main.dto.Book;
import main.exception.BookNotFoundException;
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
}
