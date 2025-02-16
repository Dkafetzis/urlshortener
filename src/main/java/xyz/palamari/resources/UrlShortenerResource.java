package xyz.palamari.resources;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import xyz.palamari.DTOs.RedirectFormRequest;
import xyz.palamari.DTOs.RedirectPostRequest;
import xyz.palamari.entities.RedirectUrl;
import xyz.palamari.services.UrlService;

@Path("/")
@ApplicationScoped
public class UrlShortenerResource {

    @Inject
    UrlService urlService;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance redirect(String urlid);

        public static native TemplateInstance error();
    }

    @GET
    @Path("{redirectCode}")
    @Transactional
    public Response redirect(String redirectCode) {
        RedirectUrl redirectUrl = RedirectUrl.findById(redirectCode);
        if (redirectUrl != null) {
            return Response.temporaryRedirect(redirectUrl.redirectUrl).build();
        } else {
            return Response.status(404).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRedirect(RedirectPostRequest redirectPostRequest) {
        String urlId = urlService.createRedirectUrl(redirectPostRequest.username(), redirectPostRequest.redirectUrl());
        if (urlId != null) {
            return Response.ok(urlId).build();
        } else {
            return Response.status(404).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    @Transactional // Have to put this here for now because something breaks with rest reactive
    public TemplateInstance createRedirect(@BeanParam RedirectFormRequest redirectFormRequest) {
        String urlId = urlService.createRedirectUrl(redirectFormRequest.username(), redirectFormRequest.redirectUrl());
        if (urlId != null) {
            return Templates.redirect(urlId);
        } else {
            return Templates.error();
        }
    }
}
