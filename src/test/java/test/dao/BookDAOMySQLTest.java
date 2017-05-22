package test.dao;

import main.dao.BookDAOMySQL;
import main.dto.Book;
import main.dto.Location;
import main.exception.ConnectionAlreadyClosedException;
import main.util.DBConnectorMySQL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

	@Test
	public void successfulGetLocationsFromBookTest() throws ConnectionAlreadyClosedException {
		BookDAOMySQL dao = mock(BookDAOMySQL.class);
		List<Location> locations = new ArrayList<>();
		locations.add(new Location(1L, 2.1230, 2.123, "name"));

		when(dao.getCitiesFromBook(anyString())).
				thenReturn(locations);

		assertThat(dao.getCitiesFromBook(anyString()),is(locations));
	}

	@Test
	public void failedGetLocationsFromBookTest() throws ConnectionAlreadyClosedException {
		BookDAOMySQL dao = mock(BookDAOMySQL.class);
		when(dao.getCitiesFromBook(anyString())).
				thenReturn(null);

		assertThat(dao.getCitiesFromBook(anyString()), is(nullValue()));
	}

	@Test
    public void successfulGetBooksAndCitiesFromAuthorTest() throws ConnectionAlreadyClosedException {
	    List<Book> books = new ArrayList<>();

	    BookDAOMySQL dao = mock(BookDAOMySQL.class);
	    when(dao.getBooksAndCitiesFromAuthor(anyString())).
                thenReturn(books);

	    assertThat(dao.getBooksAndCitiesFromAuthor(anyString()), is(books));
    }

    @Test
    public void failedGetBooksAndCitiesFromAuthorTest() throws ConnectionAlreadyClosedException {
	    BookDAOMySQL dao = mock(BookDAOMySQL.class);
	    when(dao.getBooksAndCitiesFromAuthor(anyString())).
                thenReturn(null);

	    assertThat(dao.getBooksAndCitiesFromAuthor(anyString()), is(nullValue()));
    }

    @Test
	public void successfulGetBooksFromLatitudeLongitudeTest() throws ConnectionAlreadyClosedException {
		List<Book> books = new ArrayList<>();

		BookDAOMySQL dao = mock(BookDAOMySQL.class);
		when(dao.getBooksFromLatLong(anyDouble(), anyDouble(),  anyInt())).
				thenReturn(books);

		assertThat(dao.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt()), is(books));
	}

	@Test
	public void failedGetBooksFromLatitudeLongitudeTest() throws ConnectionAlreadyClosedException {
		BookDAOMySQL dao = mock(BookDAOMySQL.class);
		when(dao.getBooksFromLatLong(anyDouble(),anyDouble(), anyInt())).
				thenReturn(null);

		assertThat(dao.getBooksFromLatLong(anyDouble(),anyDouble(),anyInt()), is(nullValue()));
	}

	@Test
	public void successfulGetAuthorsAndBooksFromCities() throws ConnectionAlreadyClosedException {
		List<Book> books = new ArrayList<>();

		BookDAOMySQL dao = mock(BookDAOMySQL.class);
		when(dao.getAuthorsAndBooksFromCity(anyString())).
				thenReturn(books);

		assertThat(dao.getAuthorsAndBooksFromCity(anyString()), is(books));
	}

	@Test
	public void failedGetAuthorsAndBooksFromCities() throws ConnectionAlreadyClosedException {
		BookDAOMySQL dao = mock(BookDAOMySQL.class);
		when(dao.getAuthorsAndBooksFromCity(anyString())).
				thenReturn(null);

		assertThat(dao.getAuthorsAndBooksFromCity(anyString()), is(nullValue()));
	}

	// Has to be changed to an integration test.
	/*
	@Test
	public void someTest() throws ConnectionAlreadyClosedException {
		BookDAOMySQL dao = new BookDAOMySQL();
		List<Book> books = dao.getAuthorsAndBooksFromCity("Washington");

		for (Book book : books) {
			System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthors().get(0).getName());
		}
		System.out.println("\n ------------------------------------------------------------\n");

		books = dao.getBooksFromLatLong(43.4125, 23.225, 100);

		for (Book book : books) {
			System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthors().get(0).getName() + ", Mentioned locations: " + book.getLocationsWithinRadius().size());
		}
	}
	*/
}
