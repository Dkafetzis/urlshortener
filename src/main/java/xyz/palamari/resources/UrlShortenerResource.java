package xyz.palamari.resources;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.UUID;
import xyz.palamari.DTOs.RedirectPostRequest;
import xyz.palamari.entities.RedirectUrl;
import xyz.palamari.entities.RedirectUser;

@Path("/")
@ApplicationScoped
public class UrlShortenerResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    @GET
    @Path("{redirectCode}")
    @Transactional
    public Response redirect(UUID redirectCode) {
        RedirectUrl redirectUrl = RedirectUrl.findById(redirectCode);
        if (redirectUrl != null) {
            return Response.temporaryRedirect(redirectUrl.redirectUrl).build();
        } else {
            return Response.status(404).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createRedirect(RedirectPostRequest redirectPostRequest) {
        RedirectUser redirectUser =
                RedirectUser.find("username", redirectPostRequest.username()).firstResult();
        if (redirectUser != null) {
            RedirectUrl url = new RedirectUrl();
            url.redirectUser = redirectUser;
            url.redirectUrl = redirectPostRequest.redirectUrl();
            url.persist();
            redirectUser.redirectUrls.add(url);
            redirectUser.persist();
            return Response.ok(url.id).build();
        } else {
            return Response.status(404).build();
        }
    }
}
