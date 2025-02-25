import static io.restassured.RestAssured.given;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

@QuarkusIntegrationTest
public class NativeAgentProfileIT {

    // Run this test to make sure that native image works and to generate the needed json files for native profile
    // creation

    @Test
    public void testAddUser() {
        JsonObject addUserJson =
                Json.createObjectBuilder().add("username", "testuser").build();

        given().contentType("application/json")
                .body(addUserJson.toString())
                .when()
                .post("/users")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());

        JsonObject addURL = Json.createObjectBuilder()
                .add("username", "testuser")
                .add("redirectUrl", "http://locahost:1234")
                .build();

        given().contentType("application/json")
                .body(addURL.toString())
                .when()
                .post()
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
    }
}
