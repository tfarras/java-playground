package dev.umbra.server.factories;

import dev.umbra.server.user.dto.RegisterUserRequest;

public class RegisterUserRequestFactory extends JsonFactory<RegisterUserRequest> {
    private String email = "login@example.com";
    private String password = "StrongPass123!";
    private int kdfType = 1;
    private int kdfIterations = 1;

    public static RegisterUserRequestFactory init() {
        return new RegisterUserRequestFactory();
    }

    @Override
    public RegisterUserRequest buildObject() {
        return new RegisterUserRequest(email, password, kdfType, kdfIterations);
    }

    public RegisterUserRequestFactory setEmail(String email) {
        this.email = email;

        return this;
    }

    public RegisterUserRequestFactory setPassword(String password) {
        this.password = password;

        return this;
    }

    public RegisterUserRequestFactory setKdfType(int kdfType) {
        this.kdfType = kdfType;

        return this;
    }

    public RegisterUserRequestFactory setKdfIterations(int kdfIterations) {
        this.kdfIterations = kdfIterations;

        return this;
    }
}
