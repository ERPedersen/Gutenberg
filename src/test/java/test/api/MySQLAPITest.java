package test.api;

import main.api.MySQLAPI;
import main.facade.BookFacadeMySQL;
import main.facade.IBookFacadeMySQL;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

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
}
