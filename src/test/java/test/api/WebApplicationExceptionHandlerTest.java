package test.api;

import main.api.WebApplicationExceptionHandler;
import org.junit.Test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class WebApplicationExceptionHandlerTest {

    @Test
    public void toResponseTest() {
        WebApplicationExceptionHandler waeh = new WebApplicationExceptionHandler();

        Response response = waeh.toResponse(new WebApplicationException());

        assertThat(response.getStatus(), is(500));
    }
}
