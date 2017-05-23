package test.api;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import com.jayway.restassured.response.Response;
import main.dto.Book;
import main.dto.Location;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

public class RestAssuredMySQL {

	private Response response;
	private Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
			.create();

	@Test
	public void testConnectionOpen() {
		response = given()
				.when()
				.get("http://localhost:8080/api/mysql/")
				.then()
				.statusCode(200)
				.extract().response();

		String jsonString = response.asString();
		Map<String, Object> map = gson.fromJson(jsonString, new TypeToken<Map<String, Object>>() {}.getType());
		assertThat(map.get("code"), equalTo("200"));
		assertThat(map.get("msg"), equalTo("You have successfully connected to the MySQL API!"));
	}

	@Test
	public void successfulTestGetBooksFromLatLong() {
		response = given()
				.when()
				.get("http://localhost:8080/api/mysql/book/location?lat=52.18935&long=-2.22001&rad=50")
				.then()
				.contentType(JSON)
				.statusCode(200)
				.extract().response();

		String jsonString = response.asString();

		JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
		JsonArray jsonArray = jsonObject.getAsJsonArray("data");
		List<Book> books = gson.fromJson(jsonArray, new TypeToken<List<Book>>() {}.getType());

		assertThat(books, hasSize(greaterThan(0)));
	}

	@Test
	public void unsuccessfulTestGetBooksFromLatLong() {
		response = given()
				.when()
				.get("http://localhost:8080/api/mysql/book/location?lat=420420.0&long=-696969.0&rad=666")
				.then()
				.contentType(JSON)
				.statusCode(400)
				.extract().response();
	}

	@Test
	public void successfulTestGetBooksAndCitiesFromAuthor() {

		response = given()
				.when()
				.get("http://localhost:8080/api/mysql/book/author?q=Thomas Clarkson")
				.then()
				.contentType(JSON)
				.statusCode(200)
				.extract().response();

		String jsonString = response.asString();

		JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
		JsonArray jsonArray = jsonObject.getAsJsonArray("data");
		List<Book> books = gson.fromJson(jsonArray, new TypeToken<List<Book>>() {}.getType());

		assertThat(books, hasSize(greaterThan(0)));

	}

	@Test
	public void unsuccessfulTestGetBooksAndCitiesFromAuthor() {
		response = given()
				.when()
				.get("http://localhost:8080/api/mysql/book/author?q=Hunk SlabChest")
				.then()
				.contentType(JSON)
				.statusCode(400)
				.extract().response();
	}

	@Test
	public void successfulTestGetCitiesFromBook() {

		response = given()
				.when()
				.get("http://localhost:8080/api/mysql/location?q=What Peace Means")
				.then()
				.statusCode(200)
				.extract().response();

		String jsonString = response.asString();

		JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
		JsonArray jsonArray = jsonObject.getAsJsonArray("data");
		List<Location> locations = gson.fromJson(jsonArray, new TypeToken<List<Location>>() {}.getType());

		assertThat(locations, hasSize(greaterThan(0)));
	}

	@Test
	public void unsuccessfulTestGetCitiesFromBook() {

		response = given()
				.when()
				.get("http://localhost:8080/api/mysql/location?=Bonerbutt: The Collected Works")
				.then()
				.statusCode(400)
				.extract().response();

	}

	@Test
	public void successfulTestGetAuthorsAndBooksFromCity() {

		response = given()
				.when()
				.get("http://localhost:8080/api/mysql/book/city?q=Copenhagen")
				.then()
				.contentType(JSON)
				.statusCode(200)
				.extract().response();

		String jsonString = response.asString();

		JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
		JsonArray jsonArray = jsonObject.getAsJsonArray("data");
		List<Book> books = gson.fromJson(jsonArray, new TypeToken<List<Book>>() {}.getType());

		assertThat(books, hasSize(greaterThan(0)));
	}

	@Test
	public void unsuccessfulTestGetAuthorsAndBooksFromCity() {

		response = given()
				.when()
				.get("http://localhost:8080/api/mysql/book/city?q=New Donk City")
				.then()
				.contentType(JSON)
				.statusCode(400)
				.extract().response();

	}
}