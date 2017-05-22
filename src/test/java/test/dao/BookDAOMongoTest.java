package test.dao;

import main.dao.BookDAOMongo;
import main.util.DBConnectorMongo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BookDAOMongoTest {

    BookDAOMongo dao;

    @Test
    public void defaultConstructorTest() {
        dao = new BookDAOMongo();

        assertThat(dao, is(notNullValue()));
    }

    @Test
    public void dependencyConstructorTest() {
        DBConnectorMongo connector = new DBConnectorMongo();
        dao = new BookDAOMongo(connector);

        assertThat(dao, is(notNullValue()));
    }
}
