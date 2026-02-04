package dev.umbra.server.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtService {
    // TODO: Move to ENV variables (Need to learn how to operate with it)
    private static final SecretKey KEY =
            Keys.hmacShaKeyFor(
                    "CHANGE_ME_TO_A_LONG_RANDOM_SECRET_AT_LEAST_32_BYTES"
                            .getBytes()
            );

    private static final long EXPIRATION_SECONDS = 900; // 15 min

    public String generateToken(UUID userId, String email) {
        Instant now = Instant.now();

        return Jwts.builder()
                .subject(userId.toString())
                .claim("email", email)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(EXPIRATION_SECONDS)))
                .signWith(KEY)
                .compact();

    }

    public long getExpirationSeconds() {
        return EXPIRATION_SECONDS;
    }
}
