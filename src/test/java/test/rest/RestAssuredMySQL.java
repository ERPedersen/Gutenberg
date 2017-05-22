/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.rest;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import com.jayway.restassured.response.Response;
import main.dto.Author;
import main.dto.Book;
import main.dto.Location;
import main.exception.BookNotFoundException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.List;


/**
 * @author Private
 */
public class RestAssuredMySQL {

    Response response;
    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create();

    public RestAssuredMySQL() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Test
    public void testConnectionOpen() {
        given()
                .when()
                .get("http://localhost:8080/api/mysql/test/")
                .then()
                .statusCode(200);
    }

    @Test
    public void successfulTestGetCitiesFromBook() {

        response = given()
                .when()
                .get("http://localhost:8080/api/mysql/frombook/What Peace Means")
                .then()
                .statusCode(200)
                .extract().response();

        String jsonString = response.asString();

        JsonObject data = gson.fromJson(jsonString, JsonObject.class);
        JsonArray jsonArray = (JsonArray) data.get("data");
        List<Location> cities = gson.fromJson(jsonArray, new TypeToken<List<Location>>() {}.getType());

        assertThat(cities, hasSize(greaterThan(0)));

        assertThat(cities.get(0).getName(), equalTo("Same"));
        assertThat(cities.get(0).getUID(), equalTo(150276L));
        assertThat(cities.get(0).getLatitude(), equalTo(-4.06667));
        assertThat(cities.get(0).getLongitude(), equalTo(37.73333));
    }

    @Test
    public void unsuccessfulTestGetCitiesFromBook() {

        response = given()
                .when()
                .get("http://localhost:8080/api/mysql/frombook/Bonerbutt: The Collected Works")
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
                .get("http://localhost:8080/api/mysql/fromcity/Copenhagen")
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

        assertThat(books.get(0).getTitle(), equalTo("Drake, Nelson and Napoleon"));
        assertThat(books.get(0).getUID(), equalTo(15299L));
        assertThat(books.get(0).getAuthors(), hasSize(1));
        assertThat(authors.get(0).getName(), equalTo("Walter Runciman"));
        assertThat(authors.get(0).getUID(), equalTo(26L));
        assertThat(books.get(0).getLocations(), hasSize(equalTo(0)));
        assertThat(books.get(0).getText(), equalTo("15299.txt"));
    }

    @Test
    public void unsuccessfulTestGetAuthorsAndBooksFromCity() {

        response = given()
                .when()
                .get("http://localhost:8080/api/mysql/fromcity/New Donk City")
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
                .get("http://localhost:8080/api/mysql/fromauthor/Edith Wharton")
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
                .get("http://localhost:8080/api/mysql/fromauthor/Hunk SlabChest")
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

    @Test
    public void successfulTestGetBooksFromLatLong() {
        response = given()
                .when()
                .get("http://localhost:8080/api/mysql/fromlatlong/52.18935/-2.22001/50")
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
    public void unsuccessfulTestGetBooksFromLatLong() {
        response = given()
                .when()
                .get("http://localhost:8080/api/mysql/fromlatlong/420420.0/-696969.0/666")
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

}