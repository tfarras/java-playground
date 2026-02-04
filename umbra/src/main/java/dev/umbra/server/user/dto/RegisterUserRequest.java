package dev.umbra.server.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterUserRequest(

        @Email
        @NotBlank
        String email,

        @NotBlank
        String password,

        @NotNull
        Integer kdfType,

        @NotNull
        Integer kdfIterations
) {}