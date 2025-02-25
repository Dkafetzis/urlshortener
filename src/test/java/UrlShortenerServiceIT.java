import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.json.Json;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import xyz.palamari.resources.UrlShortenerResource;

@QuarkusTest
@TestHTTPEndpoint(UrlShortenerResource.class)
public class UrlShortenerServiceIT {

    @Test
    public void testRedirectCreationUrl() {

        // Add an url with a valid username
        given().contentType("application/json")
                .body(Json.createObjectBuilder()
                        .add("username", "testurluser")
                        .add("redirectUrl", "http://localhost:1516")
                        .build()
                        .toString())
                .when()
                .post()
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());

        // Add an url that has been added before
        // The returned code should be the same as the first time it was entered
        given().contentType("application/json")
                .body(Json.createObjectBuilder()
                        .add("username", "testurluser")
                        .add("redirectUrl", "http://localhost:1234")
                        .build()
                        .toString())
                .when()
                .post()
                .then()
                .contentType("text/plain")
                .body(containsString("bdc7fa88"))
                .statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Test
    public void testRedirectUrl() {

        // For some reason the request resolves after the redirect
        given().get("/{redirectUrl}", "3683551a").then().statusCode(Response.Status.OK.getStatusCode());

        given().get("/{redirectUrl}", "12345jls").then().statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }
}
