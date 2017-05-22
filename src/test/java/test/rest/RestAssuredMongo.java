package test.rest;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import com.jayway.restassured.response.Response;
import main.dto.Author;
import main.dto.Book;
import main.dto.Location;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

/**
 * Created by Private on 5/19/2017.
 */
public class RestAssuredMongo {

    Response response;
    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create();

    public RestAssuredMongo() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Test
    public void testConnectionOpen() {
        given()
                .when()
                .get("http://localhost:8080/api/mongo/test/")
                .then()
                .statusCode(200);
    }

    @Test
    public void successfulTestGetCitiesFromBook() {

        response = given()
                .when()
                .get("http://localhost:8080/api/mongo/frombook/The Truce of God")
                .then()
                .statusCode(200)
                .extract().response();

        String jsonString = response.asString();

        JsonObject data = gson.fromJson(jsonString, JsonObject.class);
        JsonArray jsonArray = (JsonArray) data.get("data");
        List<Location> cities = gson.fromJson(jsonArray, new TypeToken<List<Location>>() {}.getType());

        assertThat(cities, hasSize(greaterThan(0)));
    }

    @Test
    public void unsuccessfulTestGetCitiesFromBook() {

        response = given()
                .when()
                .get("http://localhost:8080/api/mongo/frombook/Bonerbutt: The Collected Works")
                .then()
                .statusCode(200)
                .extract().response();

        String jsonString = response.asString();

        JsonObject data = gson.fromJson(jsonString, JsonObject.class);
        JsonArray jsonArray = (JsonArray) data.get("data");
        List<Location> cities = gson.fromJson(jsonArray, new TypeToken<List<Location>>() {}.getType());

        assertThat(cities, hasSize(equalTo(0)));

    }

    @Test
    public void successfulTestGetAuthorsAndBooksFromCity() {

        response = given()
                .when()
                .get("http://localhost:8080/api/mongo/fromcity/Copenhagen")
                .then()
                .contentType(JSON)
                .statusCode(200)
                .extract().response();

        String jsonString = response.asString();

        JsonObject data = gson.fromJson(jsonString, JsonObject.class);
        JsonArray jsonArray = (JsonArray) data.get("data");
        List<Book> books = gson.fromJson(jsonArray, new TypeToken<List<Book>>() {}.getType());

        assertThat(books, hasSize(greaterThan(0)));

        List<Author> authors = books.get(0).getAuthors();
    }

    @Test
    public void unsuccessfulTestGetAuthorsAndBooksFromCity() {

        response = given()
                .when()
                .get("http://localhost:8080/api/mongo/fromcity/New Donk City")
                .then()
                .contentType(JSON)
                .statusCode(200)
                .extract().response();

        String jsonString = response.asString();

        JsonObject data = gson.fromJson(jsonString, JsonObject.class);
        JsonArray jsonArray = (JsonArray) data.get("data");
        List<Book> books = gson.fromJson(jsonArray, new TypeToken<List<Book>>() {}.getType());

        assertThat(books, hasSize(0));

    }

    @Test
    public void successfulTestGetBooksAndCitiesFromAuthor() {

        response = given()
                .when()
                .get("http://localhost:8080/api/mongo/fromauthor/Thomas Clarkson")
                .then()
                .contentType(JSON)
                .statusCode(200)
                .extract().response();

        String jsonString = response.asString();

        JsonObject data = gson.fromJson(jsonString, JsonObject.class);
        JsonArray jsonArray = (JsonArray) data.get("data");
        List<Book> books = gson.fromJson(jsonArray, new TypeToken<List<Book>>() {}.getType());

        assertThat(books, hasSize(greaterThan(0)));

    }

    @Test
    public void unsuccessfulTestGetBooksAndCitiesFromAuthor() {
        response = given()
                .when()
                .get("http://localhost:8080/api/mongo/fromauthor/Hunk SlabChest")
                .then()
                .contentType(JSON)
                .statusCode(200)
                .extract().response();

        String jsonString = response.asString();

        JsonObject data = gson.fromJson(jsonString, JsonObject.class);
        JsonArray jsonArray = (JsonArray) data.get("data");
        List<Book> books = gson.fromJson(jsonArray, new TypeToken<List<Book>>() {}.getType());

        assertThat(books, hasSize(equalTo(0)));
    }

    @Test(expected = AssertionError.class)
    public void successfulTestGetBooksFromLatLong() {
        response = given()
                .when()
                .get("http://localhost:8080/api/mongo/fromlatlong/52.18935/-2.22001/50")
                .then()
                .contentType(JSON)
                .statusCode(200)
                .extract().response();

        String jsonString = response.asString();

        List<Book> books = gson.fromJson(jsonString, new TypeToken<List<Book>>() {
        }.getType());

        assertThat(books, hasSize(greaterThan(0)));
    }

    @Test(expected = AssertionError.class)
    public void unsuccessfulTestGetBooksFromLatLong() {
        response = given()
                .when()
                .get("http://localhost:8080/api/mongo/fromlatlong/420420.0/-696969.0/666")
                .then()
                .contentType(JSON)
                .statusCode(200)
                .extract().response();

        String jsonString = response.asString();

        List<Book> books = gson.fromJson(jsonString, new TypeToken<List<Book>>() {
        }.getType());

        assertThat(books, hasSize(equalTo(0)));
    }
}
