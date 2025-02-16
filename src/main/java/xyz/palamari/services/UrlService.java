package xyz.palamari.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.net.URI;
import xyz.palamari.entities.RedirectUrl;
import xyz.palamari.entities.RedirectUser;

@ApplicationScoped
public class UrlService {

    @Transactional
    public String createRedirectUrl(String username, URI redirectUrl) {
        RedirectUser redirectUser = RedirectUser.find("username", username).firstResult();
        if (redirectUser != null) {
            RedirectUrl url = new RedirectUrl();
            url.redirectUser = redirectUser;
            url.redirectUrl = redirectUrl;
            url.persist();
            redirectUser.redirectUrls.add(url);
            redirectUser.persist();
            return url.id;
        } else {
            return null;
        }
    }
}
