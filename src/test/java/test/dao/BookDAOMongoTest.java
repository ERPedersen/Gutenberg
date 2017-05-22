package test.dao;

import main.dao.BookDAOMongo;
import main.dto.Book;
import main.dto.Location;
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
    public void successfulGetLocationsFromBookTest() {
        BookDAOMongo dao = mock(BookDAOMongo.class);
        List<Location> locations = new ArrayList<>();
        locations.add(new Location(1L, 2.1230, 2.123, "name"));

        when(dao.getCitiesFromBook(anyString())).
                thenReturn(locations);

        assertThat(dao.getCitiesFromBook(anyString()),is(locations));
    }

    @Test
    public void failedGetLocationsFromBookTest() {
        BookDAOMongo dao = mock(BookDAOMongo.class);
        when(dao.getCitiesFromBook(anyString())).
                thenReturn(null);

        assertThat(dao.getCitiesFromBook(anyString()), is(nullValue()));
    }

    @Test
    public void successfulGetBooksAndCitiesFromAuthorTest() {
        List<Book> books = new ArrayList<>();

        BookDAOMongo dao = mock(BookDAOMongo.class);
        when(dao.getBooksAndCitiesFromAuthor(anyString())).
                thenReturn(books);

        assertThat(dao.getBooksAndCitiesFromAuthor(anyString()), is(books));
    }

    @Test
    public void failedGetBooksAndCitiesFromAuthorTest() {
        BookDAOMongo dao = mock(BookDAOMongo.class);
        when(dao.getBooksAndCitiesFromAuthor(anyString())).
                thenReturn(null);

        assertThat(dao.getBooksAndCitiesFromAuthor(anyString()), is(nullValue()));
    }

    @Test
    public void successfulGetBooksFromLatitudeLongitudeTest() {
        List<Book> books = new ArrayList<>();

        BookDAOMongo dao = mock(BookDAOMongo.class);
        when(dao.getBooksFromLatLong(anyDouble(), anyDouble(),  anyInt())).
                thenReturn(books);

        assertThat(dao.getBooksFromLatLong(anyDouble(), anyDouble(), anyInt()), is(books));
    }

    @Test
    public void failedGetBooksFromLatitudeLongitudeTest() {
        BookDAOMongo dao = mock(BookDAOMongo.class);
        when(dao.getBooksFromLatLong(anyDouble(),anyDouble(), anyInt())).
                thenReturn(null);

        assertThat(dao.getBooksFromLatLong(anyDouble(),anyDouble(),anyInt()), is(nullValue()));
    }

    @Test
    public void successfulGetAuthorsAndBooksFromCities() {
        List<Book> books = new ArrayList<>();

        BookDAOMongo dao = mock(BookDAOMongo.class);
        when(dao.getAuthorsAndBooksFromCity(anyString())).
                thenReturn(books);

        assertThat(dao.getAuthorsAndBooksFromCity(anyString()), is(books));
    }

    @Test
    public void failedGetAuthorsAndBooksFromCities() {
        BookDAOMongo dao = mock(BookDAOMongo.class);
        when(dao.getAuthorsAndBooksFromCity(anyString())).
                thenReturn(null);

        assertThat(dao.getAuthorsAndBooksFromCity(anyString()), is(nullValue()));
    }

}
