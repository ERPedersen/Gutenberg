package test.api;

import main.api.MongoAPI;
import main.dto.Book;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MongoApiTest {

    @Test
    public void defaultConstructorTest() {
        MongoAPI api = new MongoAPI();

        assertThat(api, is(notNullValue()));
    }
}
