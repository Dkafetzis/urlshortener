import static io.restassured.RestAssured.given;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.json.Json;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import xyz.palamari.resources.UserResource;

@QuarkusTest
@TestHTTPEndpoint(UserResource.class)
public class UserServiceIT {

    @Test
    public void testAddUser() {

        // Make a user with a unique username, this should work
        given().contentType("application/json")
                .body(Json.createObjectBuilder()
                        .add("username", "testuser")
                        .build()
                        .toString())
                .when()
                .post()
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());

        // Try to make a user with an existing username, this should fail
        given().contentType("application/json")
                .body(Json.createObjectBuilder()
                        .add("username", "testurluser")
                        .build()
                        .toString())
                .when()
                .post()
                .then()
                .statusCode(Response.Status.CONFLICT.getStatusCode());
    }
}
