package test.dao;

import main.dao.BookDAOMongo;
import main.dto.Book;
import main.dto.Location;
import main.util.DBConnectorMongo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookDAOMongoTest {

    BookDAOMongo dao;

    @Test
    public void defaultConstructorTest() {
        dao = new BookDAOMongo();

        assertThat(dao, is(notNullValue()));
    }

    @Test
    public void dependencyInjectionTest() {
        DBConnectorMongo connector = new DBConnectorMongo();
        dao = new BookDAOMongo(connector);

        assertThat(dao, is(notNullValue()));
    }

}
