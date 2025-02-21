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
import xyz.palamari.DTOs.RedirectFormRequest;
import xyz.palamari.DTOs.RedirectPostRequest;
import xyz.palamari.entities.RedirectUrl;
import xyz.palamari.services.UrlService;

@Path("/")
@ApplicationScoped
public class UrlShortenerResource {

    @Inject
    UrlService urlService;

    @Inject
    Template error;

    @CheckedTemplate
    public static class UrlShortenerTemplates {
        public static native TemplateInstance redirect(String urlid);
    }

    @GET
    @Path("{redirectCode}")
    @Transactional
    public Response redirect(String redirectCode) {
        RedirectUrl redirectUrl = RedirectUrl.findById(redirectCode);
        if (redirectUrl != null) {
            redirectUrl.hits++;
            redirectUrl.persist();
            return Response.temporaryRedirect(redirectUrl.redirectUrl).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRedirect(RedirectPostRequest redirectPostRequest) {
        String urlId = urlService.createRedirectUrl(redirectPostRequest.username(), redirectPostRequest.redirectUrl());
        if (urlId != null) {
            return Response.ok(urlId).status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    @Transactional // Have to put this here for now because something breaks with rest reactive
    public TemplateInstance createRedirect(@BeanParam RedirectFormRequest redirectFormRequest) {
        String urlId = urlService.createRedirectUrl(redirectFormRequest.username(), redirectFormRequest.redirectUrl());
        if (urlId != null) {
            return UrlShortenerTemplates.redirect(urlId);
        } else {
            return error.instance();
        }
    }
}
