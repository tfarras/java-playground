package dev.umbra.server.user.rto;

import dev.umbra.server.user.User;

import java.time.Instant;
import java.util.UUID;

public record UserRto(
        UUID id,
        String email,
        int kdfType,
        int kdfIterations,
        Instant createdAt
) {
    public static UserRto createFromUser(User user) {
        return new UserRto(user.getId(), user.getEmail(), user.getKdfType(), user.getKdfIterations(), user.getCreatedAt());
    }
}