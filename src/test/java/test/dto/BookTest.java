package test.dto;

import main.dto.Author;
import main.dto.Book;
import main.dto.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BookTest {

    Book book;

    @Test
    public void testEmptyConstructor() {
        book = new Book();

        assertThat(book.getUID(), is(0L));
        assertThat(book.getTitle(), is(nullValue()));
        assertThat(book.getAuthors(), is(nullValue()));
        assertThat(book.getLocations(), is(nullValue()));
        assertThat(book.getText(), is(nullValue()));
    }

    @Test
    public void testConstructorWithoutUID() {
        List<Author> authors = new ArrayList<Author>();
        authors.add(new Author("L'Ron Harald"));
        List<Location> locations = new ArrayList<Location>();
        locations.add(new Location(1L, 54.906702, 9.754529, "Dybbøl"));
        locations.add(new Location(1L, 54.939615, 8.864417, "Tønder"));
        book = new Book("Den Sønderjyske Mafia!", authors, locations, "Sønderbronx er hvor det sker!");

        assertThat(book.getTitle(), is("Den Sønderjyske Mafia!"));
        assertThat(book.getAuthors().size(), is(1));
        assertThat(book.getLocations().size(), is(2));
        assertThat(book.getText(), is("Sønderbronx er hvor det sker!"));
    }


    @Test
    public void testConstructor() {
        List<Author> authors = new ArrayList<Author>();
        authors.add(new Author("L'Ron Harald"));
        List<Location> locations = new ArrayList<Location>();
        locations.add(new Location(1L, 54.906702, 9.754529, "Dybbøl"));
        locations.add(new Location(1L, 54.939615, 8.864417, "Tønder"));
		book = new Book(2L, "Den Sønderjyske Mafia!", authors, locations, "Sønderbronx er hvor det sker!");

		assertThat(book.getUID(), is(2L));
		assertThat(book.getTitle(), is("Den Sønderjyske Mafia!"));
		assertThat(book.getAuthors().size(), is(1));
		assertThat(book.getLocations().size(), is(2));
		assertThat(book.getText(), is("Sønderbronx er hvor det sker!"));
    }

	@Test
	public void testSetters() {
        List<Author> authors = new ArrayList<Author>();
        authors.add(new Author("L'Ron Harald"));
        List<Location> locations = new ArrayList<Location>();
        locations.add(new Location(1L, 54.906702, 9.754529, "Dybbøl"));
        locations.add(new Location(1L, 54.939615, 8.864417, "Tønder"));
        book = new Book(1L, "Den Sønderjyske Mafia!", authors, locations, "Sønderbronx er hvor det sker!");

        book.setUID(2L);
        book.setTitle("Den søde stenaldermand");
        authors.add(new Author("Ugga Bukka"));
        book.setAuthors(authors);
        locations.add(new Location(1L, 50.850346, 4.351721, "Brussels"));
        book.setLocations(locations);
        book.setText("RAWWWR");
        List<Location> locationsWithinRadius = new ArrayList<Location>() {{
            add(new Location());
        }};
        book.setLocationsWithinRadius(locationsWithinRadius);

        assertThat(book.getUID(), is(2L));
        assertThat(book.getTitle(), is("Den søde stenaldermand"));
        assertThat(book.getAuthors().size(), is(2));
        assertThat(book.getLocations().size(), is(3));
        assertThat(book.getText(), is("RAWWWR"));
        assertThat(book.getLocationsWithinRadius().size(), is(1));
	}

	@Test
    public void addLocationTest() {
        Book book = new Book(1L,"title", new ArrayList<>(), new ArrayList<>(), "text");
        book.addLocation(new Location(1L, 123.123,123.123, "name"));

        assertThat(book.getLocations().get(0).getUID(), is(1L));
    }

    @Test
    public void addAuthorTest() {
        Book book = new Book(1L,"title", new ArrayList<>(), new ArrayList<>(), "text");
        book.addAuthor(new Author(1L, "name"));

        assertThat(book.getAuthors().get(0).getName(), is("name"));
    }

}
