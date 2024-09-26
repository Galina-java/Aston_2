import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetRequestTest {

    @Test
    public void testGetResponseCode200() {
        RestAssured.baseURI = "https://postman-echo.com";

        given()
                .queryParam("foo1", "bar1")
                .queryParam("foo2", "bar2")
                .when()
                .get("/get")
                .then()
                .statusCode(200)
                .body("args.foo1", equalTo("bar1"))
                .body("args.foo2", equalTo("bar2"));
    }

    @Test
    public void testPostResponseCode200() {
        RestAssured.baseURI = "https://postman-echo.com";
        String requestBody = "{ \"test\": \"value\" }";
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/post")
                .then()
                .statusCode(200)
                .body("data.test", equalTo("value"));
    }

    @Test
    public void testPostWithResponseHandling() {
        RestAssured.baseURI = "https://postman-echo.com";
        String requestBody = "{ \"test\": \"value\" }";
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/post")
                .then()
                .statusCode(200) // Проверка кода ответа
                .body("data.test", equalTo("value"))
                .body("json.test", equalTo("value"));
    }

    @Test
    public void postFromDataTest() { //статус кода 500
        RestAssured.baseURI = "https://postman-echo.com";
        given()
                .log().all()
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("foo1", "bar1")
                .formParam("foo2", "bar2")
                .post("/post")
                .then()
                .statusCode(200)
                .body("form.foo1", equalTo("bar1"))
                .body("form.foo2", equalTo("bar2"));
    }

    @Test
    public void putRequestTest() {
        RestAssured.baseURI = "https://postman-echo.com";
        String requestBody = "This is expected to be sent back as part of response body.";
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/put")
                .then()
                .statusCode(200)
                .body("data", equalTo(requestBody));
    }

    @Test
    public void patchRequestTest() {
        RestAssured.baseURI = "https://postman-echo.com";
        String requestBody = "This is expected to be sent back as part of response body.";
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .patch("/patch")
                .then()
                .statusCode(200)
                .body("data", equalTo(requestBody));
    }

    @Test
    public void deleteRequestTest() {
        RestAssured.baseURI = "https://postman-echo.com";
        String requestBody = "This is expected to be sent back as part of response body.";
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .delete("/delete")
                .then()
                .statusCode(200)
                .body("data", equalTo(requestBody));
    }
}

