package xyz.palamari.DTOs;

import java.net.URI;
import org.jboss.resteasy.reactive.RestForm;

public record RedirectFormRequest(@RestForm String username, @RestForm URI redirectUrl) {}
