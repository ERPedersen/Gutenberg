package test.api;

import com.sun.jersey.api.ParamException;
import main.api.ParamExceptionHandler;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParamExceptionHandlerTest {

    @Test
    public void toResponseTest() {
        ParamExceptionHandler peh = new ParamExceptionHandler();

        Response response = peh.toResponse(new ParamException.HeaderParamException(null, "name", "defaultValue"));

        assertThat(response.getStatus(), is(400));
    }
}
