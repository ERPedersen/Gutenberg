package test.api;

import com.sun.jersey.api.NotFoundException;
import main.api.NotFoundExceptionHandler;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NotFoundExceptionHandlerTest {
    @Test
    public void toResponseTest() {
        NotFoundExceptionHandler nfh = new NotFoundExceptionHandler();

        Response response = nfh.toResponse(new NotFoundException("msg"));

        assertThat(response.getStatus(), is(404));
    }
}
