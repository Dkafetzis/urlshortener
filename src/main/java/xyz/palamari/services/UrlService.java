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
        if (username.isEmpty()) {
            username = "ephemeral";
        }
        RedirectUser redirectUser = RedirectUser.find("username", username).firstResult();
        if (redirectUser != null) {
            RedirectUrl existingUrl =
                    RedirectUrl.find("redirectUrl", redirectUrl).firstResult();
            if (existingUrl != null) {
                return existingUrl.id;
            } else {
                RedirectUrl url = new RedirectUrl();
                url.redirectUser = redirectUser;
                url.redirectUrl = redirectUrl;
                url.persist();
                redirectUser.redirectUrls.add(url);
                redirectUser.persist();
                return url.id;
            }
        } else {
            return null;
        }
    }

    @Transactional
    public RedirectUrl findRedirectUrl(String redirectCode) {
        RedirectUrl redirectUrl = RedirectUrl.findById(redirectCode);
        if (redirectUrl != null) {
            redirectUrl.hits++;
            redirectUrl.persist();
        }
        return redirectUrl;
    }

    @Transactional
    public void deleteRedirectUrl(String username, String urlid) {
        RedirectUser redirectUser = RedirectUser.find("username", username).firstResult();
        RedirectUrl url = RedirectUrl.findById(urlid);
        url.delete();
        redirectUser.redirectUrls.removeIf(curl -> curl.id.equals(urlid));
        redirectUser.persist();
    }
}
