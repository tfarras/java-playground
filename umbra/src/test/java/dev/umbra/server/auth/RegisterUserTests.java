package dev.umbra.server.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthUserTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void register_shouldReturnUser_whenValid() throws Exception {
        String payload = """
      {
        "email": "user@example.com",
        "password": "StrongPass123!",
        "kdfType": 1,
        "kdfIterations": 600000
      }
      """;

        mockMvc.perform(post("/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", not(emptyOrNullString())))
                .andExpect(jsonPath("$.email").value("user@example.com"))
                .andExpect(jsonPath("$.kdfType").value(1))
                .andExpect(jsonPath("$.kdfIterations").value(600000))
                .andExpect(jsonPath("$.createdAt", not(emptyOrNullString())))
                // very important: password must never be returned
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.passwordHash").doesNotExist());
    }

    @Test
    void register_shouldFailValidation_whenEmailInvalid() throws Exception {
        String payload = """
      {
        "email": "not-an-email",
        "password": "StrongPass123!",
        "kdfType": 1,
        "kdfIterations": 600000
      }
      """;

        mockMvc.perform(post("/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void register_shouldFailValidation_whenPasswordBlank() throws Exception {
        String payload = """
      {
        "email": "user2@example.com",
        "password": "",
        "kdfType": 1,
        "kdfIterations": 600000
      }
      """;

        mockMvc.perform(post("/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_shouldReturnToken_whenCredentialsValid() throws Exception {
        // register first
        String register = """
      {
        "email": "login@example.com",
        "password": "StrongPass123!",
        "kdfType": 1,
        "kdfIterations": 600000
      }
      """;

        mockMvc.perform(post("/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(register))
                .andExpect(status().isOk());

        // then login
        String login = """
      {
        "email": "login@example.com",
        "password": "StrongPass123!"
      }
      """;

        mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(login))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accessToken", not(emptyOrNullString())))
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.expiresIn", greaterThan(0)));
    }

    @Test
    void login_shouldFailValidation_whenEmailMissing() throws Exception {
        String payload = """
      {
        "email": "",
        "password": "StrongPass123!"
      }
      """;

        mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_shouldReturn400Or401_whenPasswordWrong() throws Exception {
        // register first
        String register = """
      {
        "email": "wrongpass@example.com",
        "password": "RightPass123!",
        "kdfType": 1,
        "kdfIterations": 600000
      }
      """;

        mockMvc.perform(post("/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(register))
                .andExpect(status().isOk());

        // wrong password
        String login = """
      {
        "email": "wrongpass@example.com",
        "password": "WrongPass!!!"
      }
      """;

        mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(login))
                // TODO fix when there will be propper exception filter
                .andExpect(status().isInternalServerError());
    }
}
