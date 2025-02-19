package xyz.palamari.resources;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.jboss.resteasy.reactive.RestQuery;
import xyz.palamari.DTOs.AddUserRequest;
import xyz.palamari.entities.RedirectUrl;
import xyz.palamari.entities.RedirectUser;

@ApplicationScoped
@Path("/users")
public class UserResource {

    @Inject
    Template error;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance userPanel(String username, List<RedirectUrl> urls);
    }

    @GET
    @Transactional
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance userPanel(@RestQuery String username) {
        RedirectUser redirectUser = RedirectUser.find("username", username).firstResult();
        if (redirectUser != null) {
            return Templates.userPanel(redirectUser.username, redirectUser.redirectUrls);
        } else {
            return error.instance();
        }
    }

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
