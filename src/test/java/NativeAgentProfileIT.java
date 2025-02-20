import io.quarkus.test.junit.QuarkusIntegrationTest;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusIntegrationTest
public class NativeAgentProfileIT {


    //Run this test to make sure that native image works and to generate the needed json files for native profile creation

    @Test
    public void testAddUser() {
        JsonObject addUserJson = Json.createObjectBuilder()
                .add("username", "testuser")
                .build();

        given()
                .contentType("application/json")
                .body(addUserJson.toString())
                .when()
                .post("/users")
                .then()
                .statusCode(200);

        JsonObject addURL = Json.createObjectBuilder()
                .add("username", "testuser")
                .add("redirectUrl", "http://locahost:1234")
                .build();

        given()
                .contentType("application/json")
                .body(addURL.toString())
                .when()
                .post()
                .then()
                .statusCode(200);

    }
}
