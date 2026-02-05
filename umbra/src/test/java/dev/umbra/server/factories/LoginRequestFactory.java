package dev.umbra.server.factories;

import dev.umbra.server.auth.dto.LoginRequest;
import dev.umbra.server.user.dto.RegisterUserRequest;

public class LoginRequestFactory extends JsonFactory<LoginRequest> {
    private String email = "login@example.com";
    private String password = "StrongPass123!";

    public static LoginRequestFactory init() {
        return new LoginRequestFactory();
    }

    @Override
    public LoginRequest buildObject() {
        return new LoginRequest(email, password);
    }

    public LoginRequestFactory setEmail(String email) {
        this.email = email;

        return this;
    }

    public LoginRequestFactory setPassword(String password) {
        this.password = password;

        return this;
    }
}
