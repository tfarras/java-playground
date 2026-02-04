package dev.umbra.server.auth;

import dev.umbra.server.auth.dto.LoginRequest;
import dev.umbra.server.auth.rto.LoginRto;
import dev.umbra.server.user.User;
import dev.umbra.server.user.dto.RegisterUserRequest;
import dev.umbra.server.user.rto.UserRto;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/v1/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PermitAll
    @PostMapping("register")
    public UserRto register(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        User registeredUser = authService.register(registerUserRequest);

        return UserRto.createFromUser(registeredUser);
    }

    @PermitAll
    @PostMapping("login")
    public LoginRto login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
