package dev.umbra.server.user;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private int kdfType; // e.g. 0 = PBKDF2, 1 = Argon2id

    @Column(nullable = false)
    private int kdfIterations;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    protected User() {}

    public User(String email, String passwordHash, int kdfType, int kdfIterations) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.kdfType = kdfType;
        this.kdfIterations = kdfIterations;
    }

    public UUID getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public int getKdfType() {
        return this.kdfType;
    }

    public int getKdfIterations() {
        return this.kdfIterations;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }
}
