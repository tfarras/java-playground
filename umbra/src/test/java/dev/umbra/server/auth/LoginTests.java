package dev.umbra.server.auth;

import dev.umbra.server.factories.LoginRequestFactory;
import dev.umbra.server.factories.RegisterUserRequestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LoginTests {
    private final String registerRequest = RegisterUserRequestFactory.init().buildJson();

    @Autowired
    MockMvc mockMvc;

    private ResultActions makeRequest(String endpoint, String payload) throws Exception {
        return mockMvc.perform(post("/v1/auth/" + endpoint).contentType(MediaType.APPLICATION_JSON).content(payload));
    }

    private ResultActions makeRegisterRequest(String payload) throws Exception {
        return makeRequest("register", payload);
    }

    private ResultActions makeLoginRequest(String payload) throws Exception {
        return makeRequest("login", payload);
    }

    @Test
    @DisplayName("Should succeed when everything is valid")
    void shouldSucceed() throws Exception {
        makeRegisterRequest(registerRequest)
                .andExpect(status().isOk());

        String loginRequest = LoginRequestFactory.init().buildJson();

        makeLoginRequest(loginRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accessToken", not(emptyOrNullString())))
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.expiresIn", greaterThan(0)));
    }

    @Test
    @DisplayName("Should fail when email is missing")
    void shouldFailOnMissingEmail() throws Exception {
        String payload = LoginRequestFactory.init().setEmail(null).buildJson();

        makeLoginRequest(payload)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 401 when password is wrong")
    void errorOnWrongPassword() throws Exception {
        String registerPayload = RegisterUserRequestFactory
                .init().setEmail("wrongpass@example.com").setPassword("rightPassword").buildJson();

        makeRegisterRequest(registerPayload)
                .andExpect(status().isOk());

        String loginPayload = LoginRequestFactory.init().setEmail("wrongpass@example.com").setPassword("WrongPass!!!").buildJson();

        makeLoginRequest(loginPayload)
                .andExpect(status().isUnauthorized());
    }
}
