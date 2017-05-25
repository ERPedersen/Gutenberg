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

public class RestAssuredMongo {

    Response response;
    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create();

    @Test
    public void testConnectionOpen() {
        response = given()
                .when()
                .get("http://localhost:8080/api/mongo/")
                .then()
                .statusCode(200)
                .extract().response();

        String jsonString = response.asString();
        Map<String, Object> map = gson.fromJson(jsonString, new TypeToken<Map<String, Object>>() {
        }.getType());
        assertThat(map.get("code"), equalTo("200"));
        assertThat(map.get("msg"), equalTo("You have successfully connected to the Mongo API!"));
    }

    @Test
    public void successfulTestGetBooksFromLatLong() {
        response = given()
                .when()
                .get("http://localhost:8080/api/mongo/book/location?lat=53.79391&long=-1.75206&rad=5000&lim=100")
                .then()
                .contentType(JSON)
                .statusCode(200)
                .extract().response();

        String jsonString = response.asString();

        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        JsonArray jsonArray = jsonObject.getAsJsonArray("data");
        List<Book> books = gson.fromJson(jsonArray, new TypeToken<List<Location>>() {
        }.getType());

        assertThat(books, hasSize(greaterThan(0)));
    }

//    @Test
//    public void unsuccessfulTestGetBooksFromLatLong() {
//        response = given()
//                .when()
//                .get("http://localhost:8080/api/mongo/book/location?lat=0.0&long=-0.0&rad=0&lim=100")
//                .then()
//                .contentType(JSON)
//                .statusCode(400)
//                .extract().response();
//
//        String jsonString = response.asString();
//
//        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
//        JsonArray jsonArray = jsonObject.getAsJsonArray("data");
//        List<Book> books = gson.fromJson(jsonArray, new TypeToken<List<Location>>() {
//        }.getType());
//
//        assertThat(books, hasSize(equalTo(0)));
//    }

    @Test
    public void successfulTestGetBooksAndCitiesFromAuthor() {

        response = given()
                .when()
                .get("http://localhost:8080/api/mongo/book/author?q=Thomas Clarkson")
                .then()
                .contentType(JSON)
                .statusCode(200)
                .extract().response();

        String jsonString = response.asString();

        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        JsonArray jsonArray = jsonObject.getAsJsonArray("data");
        List<Book> books = gson.fromJson(jsonArray, new TypeToken<List<Location>>() {
        }.getType());

        assertThat(books, hasSize(greaterThan(0)));
    }

    @Test
    public void unsuccessfulTestGetBooksAndCitiesFromAuthor() {
        response = given()
                .when()
                .get("http://localhost:8080/api/mongo/book/author?q=Hunk SlabChest")
                .then()
                .contentType(JSON)
                .statusCode(400)
                .extract().response();

        String jsonString = response.asString();

        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        JsonArray jsonArray = jsonObject.getAsJsonArray("data");
        List<Book> books = gson.fromJson(jsonArray, new TypeToken<List<Location>>() {
        }.getType());

        assertThat(books, hasSize(equalTo(0)));
    }

    @Test
    public void successfulTestGetCitiesFromBook() {

        response = given()
                .when()
                .get("http://localhost:8080/api/mongo/location?q=What Peace Means")
                .then()
                .statusCode(200)
                .extract().response();

        String jsonString = response.asString();

        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        JsonArray jsonArray = jsonObject.getAsJsonArray("data");
        List<Location> locations = gson.fromJson(jsonArray, new TypeToken<List<Location>>() {
        }.getType());

        assertThat(locations, hasSize(greaterThan(0)));
    }

    @Test()
    public void unsuccessfulTestGetCitiesFromBook() {

        response = given()
                .when()
                .get("http://localhost:8080/api/mongo/location?q=Bonerbutt: The Collected Works")
                .then()
                .statusCode(400)
                .extract().response();

        String jsonString = response.asString();

        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        JsonArray jsonArray = jsonObject.getAsJsonArray("data");
        List<Location> locations = gson.fromJson(jsonArray, new TypeToken<List<Location>>() {
        }.getType());

        assertThat(locations, hasSize(equalTo(0)));
    }

    @Test
    public void successfulTestGetAuthorsAndBooksFromCity() {

        response = given()
                .when()
                .get("http://localhost:8080/api/mongo/book/city?q=Copenhagen")
                .then()
                .contentType(JSON)
                .statusCode(200)
                .extract().response();

        String jsonString = response.asString();

        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        JsonArray jsonArray = jsonObject.getAsJsonArray("data");
        List<Book> books = gson.fromJson(jsonArray, new TypeToken<List<Location>>() {
        }.getType());

        assertThat(books, hasSize(greaterThan(0)));
    }

    @Test
    public void unsuccessfulTestGetAuthorsAndBooksFromCity() {

        response = given()
                .when()
                .get("http://localhost:8080/api/mongo/book/city?q=New Donk City")
                .then()
                .contentType(JSON)
                .statusCode(400)
                .extract().response();

        String jsonString = response.asString();

        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        JsonArray jsonArray = jsonObject.getAsJsonArray("data");
        List<Book> books = gson.fromJson(jsonArray, new TypeToken<List<Location>>() {
        }.getType());

        assertThat(books, hasSize(equalTo(0)));
    }
}