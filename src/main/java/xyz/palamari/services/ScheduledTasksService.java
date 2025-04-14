package xyz.palamari.services;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import xyz.palamari.entities.RedirectUrl;
import xyz.palamari.entities.RedirectUser;

@ApplicationScoped
public class ScheduledTasksService {

    @ConfigProperty(name = "cleanup.interval")
    int cleanupDays;

    @Scheduled(cron = "{cleanup.every}")
    @Transactional
    void deleteOldURLs() {
        RedirectUser ephemeralUser = RedirectUser.find("username", "ephemeral").firstResult();
        if (ephemeralUser != null) {
            List<RedirectUrl> ephemeralURLs = ephemeralUser.redirectUrls;
            List<RedirectUrl> newURLs = new ArrayList<>();
            for (RedirectUrl redirectUrl : ephemeralURLs) {
                if (Duration.between(redirectUrl.createdAt, Instant.now()).toDays() >= cleanupDays) {
                    redirectUrl.delete();
                } else {
                    newURLs.add(redirectUrl);
                }
            }
            ephemeralUser.redirectUrls = newURLs;
            ephemeralUser.persist();
        }
    }
}
