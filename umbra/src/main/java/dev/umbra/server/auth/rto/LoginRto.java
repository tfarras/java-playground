package dev.umbra.server.auth.rto;

public record LoginRto(
        String accessToken,
        String tokenType,
        long expiresIn
) {}
