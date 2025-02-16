package xyz.palamari.DTOs;

import java.net.URI;

public record RedirectPostRequest(String username, URI redirectUrl) {}
