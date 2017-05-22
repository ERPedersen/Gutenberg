package test.dao;

import main.dao.BookDAOMySQL;
import main.util.DBConnectorMySQL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
@RunWith(MockitoJUnitRunner.class)
public class BookDAOMySQLTest {

	BookDAOMySQL dao;

	@Test
	public void defaultConstructorTest() {
		dao = new BookDAOMySQL();

		assertThat(dao, is(notNullValue()));
	}

	@Test
	public void dependencyConstructorTest() {
		DBConnectorMySQL connector = new DBConnectorMySQL();
		dao = new BookDAOMySQL(connector);

		assertThat(dao, is(notNullValue()));
	}
}
