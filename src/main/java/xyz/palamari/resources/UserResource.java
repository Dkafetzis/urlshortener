package xyz.palamari.resources;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import xyz.palamari.DTOs.AddUserRequest;
import xyz.palamari.entities.RedirectUser;

@ApplicationScoped
@Path("/users")
public class UserResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addUser(AddUserRequest addUserRequest) {
        RedirectUser redirectUser = new RedirectUser();
        redirectUser.username = addUserRequest.username();
        redirectUser.persist();
        return Response.ok(redirectUser.id + " " + redirectUser.username).build();
    }
}
