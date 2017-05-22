/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.rest;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import com.google.common.reflect.TypeToken;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.restassured.response.Response;
import main.dto.Author;
import main.dto.Book;
import main.dto.Location;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.List;


/**
 *
 * @author Private
 */
public class RestAssuredMySQLIT {

    Response response;
    Gson gson= new GsonBuilder()
            .setPrettyPrinting()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create();

    public RestAssuredMySQLIT() {
    }

    @BeforeClass
    public static void setUpClass() {
    }
/*
    @Test
    public void testConnectionOpen() {
        given()
                .when()
                .get("http://localhost:8080/api/mysql/test/")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetCitiesFromBook() {

        response = given()
                .when()
                .get("http://localhost:8080/api/mysql/frombook/Ivanhoe")
                .then()
                .statusCode(200)
                .extract().response();

        String jsonString = response.asString();

        List<Location> cities = gson.fromJson(jsonString,new TypeToken<List<Location>>(){}.getType());

        assertThat(cities, hasSize(equalTo(287)));

        assertThat(cities.get(0).getName(), equalTo("Damascus"));
        assertThat(cities.get(0).getUID(), equalTo(170654L));
        assertThat(cities.get(0).getLatitude(), equalTo(33.5102));
        assertThat(cities.get(0).getLongitude(), equalTo(36.29128));
    }

    @Test
    public void testGetAuthorsAndBooksFromCity() {

        response = given()
                .when()
                .get("http://localhost:8080/api/mysql/fromcity/Washington")
                .then()
                    .contentType(JSON)
                .statusCode(200)
                .extract().response();

        String jsonString = response.asString();

        List<Book> books = gson.fromJson(jsonString,new TypeToken<List<Book>>(){}.getType());

        assertThat(books, hasSize(402));

        List<Author> authors = books.get(0).getAuthors();

        assertThat(books.get(0).getTitle(), equalTo("The United States' Constitution"));
        assertThat(books.get(0).getUID(), equalTo(5L));
        assertThat(books.get(0).getAuthors(), hasSize(1));
        assertThat(authors.get(0).getName(), equalTo(" Founding Fathers"));
        assertThat(authors.get(0).getUID(), equalTo(157L));
        assertThat(books.get(0).getLocations(), hasSize(equalTo(0)));
        assertThat(books.get(0).getText(), equalTo("5.txt"));
    }
*/
}
