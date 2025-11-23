package br.com.queli;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class api1Test {
    @Test
    void testHelloEndpoint() {
        given()
                .when().get("/api1")
                .then()
                .statusCode(200)
                .body(is("Hello from Quarkus REST"));
    }

}