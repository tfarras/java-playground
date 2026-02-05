package dev.umbra.server.auth;

import dev.umbra.server.factories.RegisterUserRequestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RegisterUserTests {

    @Autowired
    MockMvc mockMvc;

    private ResultActions makeRequest(String payload) throws Exception {
        return mockMvc.perform(post("/v1/auth/register").contentType(MediaType.APPLICATION_JSON).content(payload));
    }

    @Test
    @DisplayName("Should return user when success")
    void shouldRegisterUser() throws Exception {
        String payload = RegisterUserRequestFactory.init().buildJson();

        makeRequest(payload)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", not(emptyOrNullString())))
                .andExpect(jsonPath("$.email").value("login@example.com"))
                .andExpect(jsonPath("$.kdfType").value(1))
                .andExpect(jsonPath("$.kdfIterations").value(1))
                .andExpect(jsonPath("$.createdAt", not(emptyOrNullString())))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.passwordHash").doesNotExist());
    }

    @Test
    @DisplayName("Should fail when email is invalid")
    void shouldFailWhenEmailInvalid() throws Exception {
        String payload = RegisterUserRequestFactory.init().setEmail("not-an-email").buildJson();

        makeRequest(payload)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail when password is empty")
    void shouldFailWhenPasswordIsEmpty() throws Exception {
        String payload = RegisterUserRequestFactory.init().setPassword("").buildJson();

        makeRequest(payload)
                .andExpect(status().isBadRequest());
    }
}
