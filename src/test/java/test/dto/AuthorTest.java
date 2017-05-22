package test.dto;

import main.dto.Author;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AuthorTest {

	private Author author;

	@Test
	public void testEmptyConstructor() {
		author = new Author();
		assertThat(author.getName(), is(nullValue()));
		assertThat(author.getUID(), is(0L));
	}

	@Test
	public void testConstructorWithoutUID() {
		author = new Author("Ole");

		assertThat(author.getName(), is("Ole"));
	}

	@Test
	public void testConstructorWithUID() {
		author = new Author(2L, "Ole");

		assertThat(author.getUID(), is(2L));
		assertThat(author.getName(), is("Ole"));
	}

	@Test
	public void testSetName() {
		author = new Author("Hans");
		author.setName("Jens");

		assertThat(author.getName(), is("Jens"));
	}
}
