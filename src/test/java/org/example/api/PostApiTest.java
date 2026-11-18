package org.example.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostApiTest {

    private static RequestSpecification spec;

    @BeforeAll
    static void setUp() {
        spec = new RequestSpecBuilder()
                .setBaseUri("https://jsonplaceholder.typicode.com")
                .setContentType(ContentType.JSON)
                .log(LogDetail.URI)                     // печатать адрес запроса
                .build();
    }

    private static ResponseSpecification okJson = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON)
            .expectResponseTime(lessThan(3000L))
            .build();

// в тесте


    @Test
    @DisplayName ("Проверка Get id=1")
    void getSinglePost(){
        given()
                .spec(spec)
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("id",equalTo(1))
                .body("userId",equalTo(1))
                .body("title", not(emptyString()))
                .spec(okJson).body("id", equalTo(1));;
    }

    @Test
    @DisplayName("Проверка Get 100шт")
    void get100Posts(){
        given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @DisplayName("GET несуществующего поста возвращает 404")
    void nonExistentPostReturns404(){
        given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .when()
                .get("/posts/9999")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Проверка Get userId=1")
    void getUserId1Posts(){
        given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .queryParam("userId", 1)
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("userId", everyItem(equalTo(1)));
    }

    @Test
    @DisplayName("Проверка POST /posts")
    void postPosts(){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("title", "test");
        body.put("body", "текст");
        body.put("userId", 1);
        given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("userId", equalTo(1))
                .body("title", equalTo("test"))
                .body("body", equalTo("текст"));
    }


    @Test
    @DisplayName ("Проверка цепочки Get id=1")
    void getPostChain(){
        int userId =
        given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .extract()
                .path("userId");
        given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .pathParam("id", userId)
                .when()
                .get("users/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("email",not(emptyString()));
    }

    @Test
    @DisplayName ("Проверка времени ответа")
    void timeLapseTest(){
        given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .time(lessThan(3000L));
    }

}
