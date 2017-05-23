package test.rest;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import com.jayway.restassured.response.Response;
import main.dto.Author;
import main.dto.Book;
import main.dto.Location;
import main.exception.BookNotFoundException;
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
                .get("http://localhost:8080/api/mongo/frombook?q=The Truce of God")
                .then()
                .statusCode(200)
                .extract().response();

        String jsonString = response.asString();

        JsonArray jsonArray = gson.fromJson(jsonString, JsonArray.class);;
        List<Location> cities = gson.fromJson(jsonArray, new TypeToken<List<Location>>() {}.getType());

        assertThat(cities, hasSize(greaterThan(0)));
    }

//    @Test(expected = BookNotFoundException.class)
//    public void unsuccessfulTestGetCitiesFromBook() {
//
//        response = given()
//                .when()
//                .get("http://localhost:8080/api/mongo/frombook?q=Bonerbutt: The Collected Works")
//                .then()
//                .statusCode(404)
//                .extract().response();
////
////        String jsonString = response.asString();
////
////        JsonObject data = gson.fromJson(jsonString, JsonObject.class);
////        JsonArray jsonArray = (JsonArray) data.get("data");
////        List<Location> cities = gson.fromJson(jsonArray, new TypeToken<List<Location>>() {}.getType());
////
////        assertThat(cities, hasSize(equalTo(0)));
//
//    }

    @Test
    public void successfulTestGetAuthorsAndBooksFromCity() {

        response = given()
                .when()
                .get("http://localhost:8080/api/mongo/fromcity?q=Copenhagen")
                .then()
                .contentType(JSON)
                .statusCode(200)
                .extract().response();

        String jsonString = response.asString();

        JsonArray jsonArray = gson.fromJson(jsonString, JsonArray.class);
        List<Book> books = gson.fromJson(jsonArray, new TypeToken<List<Book>>() {}.getType());


        assertThat(books, hasSize(greaterThan(0)));

        List<Author> authors = books.get(0).getAuthors();
    }

//    @Test(expected = BookNotFoundException.class)
//    public void unsuccessfulTestGetAuthorsAndBooksFromCity() {
//
//        response = given()
//                .when()
//                .get("http://localhost:8080/api/mongo/fromcity?q=New Donk City")
//                .then()
//                .contentType(JSON)
//                .statusCode(404)
//                .extract().response();
//
////        String jsonString = response.asString();
////
////        JsonObject data = gson.fromJson(jsonString, JsonObject.class);
////        JsonArray jsonArray = (JsonArray) data.get("data");
////        List<Book> books = gson.fromJson(jsonArray, new TypeToken<List<Book>>() {}.getType());
////
////        assertThat(books, hasSize(0));
//
//    }

    @Test
    public void successfulTestGetBooksAndCitiesFromAuthor() {

        response = given()
                .when()
                .get("http://localhost:8080/api/mongo/fromauthor?q=Thomas Clarkson")
                .then()
                .contentType(JSON)
                .statusCode(200)
                .extract().response();

        String jsonString = response.asString();

        JsonArray jsonArray = gson.fromJson(jsonString, JsonArray.class);
        List<Book> books = gson.fromJson(jsonArray, new TypeToken<List<Book>>() {}.getType());

        assertThat(books, hasSize(greaterThan(0)));

    }

//    @Test(expected = BookNotFoundException.class)
//    public void unsuccessfulTestGetBooksAndCitiesFromAuthor() {
//        response = given()
//                .when()
//                .get("http://localhost:8080/api/mongo/fromauthor?q=Hunk SlabChest")
//                .then()
//                .contentType(JSON)
//                .statusCode(200)
//                .extract().response();
//
////        String jsonString = response.asString();
////
////        JsonObject data = gson.fromJson(jsonString, JsonObject.class);
////        JsonArray jsonArray = (JsonArray) data.get("data");
////        List<Book> books = gson.fromJson(jsonArray, new TypeToken<List<Book>>() {}.getType());
////
////        assertThat(books, hasSize(equalTo(0)));
//    }

    @Test
    public void successfulTestGetBooksFromLatLong() {
        response = given()
                .when()
                .get("http://localhost:8080/api/mongo/fromlatlong?lat=52.18935&long=-2.22001&rad=50")
                .then()
                .contentType(JSON)
                .statusCode(200)
                .extract().response();

        String jsonString = response.asString();

        JsonArray jsonArray = gson.fromJson(jsonString, JsonArray.class);
        List<Book> books = gson.fromJson(jsonArray, new TypeToken<List<Book>>() {}.getType());

        assertThat(books, hasSize(greaterThan(0)));
    }

//    @Test(expected = BookNotFoundException.class)
//    public void unsuccessfulTestGetBooksFromLatLong() {
//        response = given()
//                .when()
//                .get("http://localhost:8080/api/mongo/fromlatlong?lat=420420.0&long=-696969.0&rad=666")
//                .then()
//                .contentType(JSON)
//                .statusCode(404)
//                .extract().response();
//
////        String jsonString = response.asString();
////
////        JsonObject data = gson.fromJson(jsonString, JsonObject.class);
////        JsonArray jsonArray = (JsonArray) data.get("data");
////        List<Book> books = gson.fromJson(jsonArray, new TypeToken<List<Book>>() {}.getType());
////
////        assertThat(books, hasSize(equalTo(0)));
//    }
}
