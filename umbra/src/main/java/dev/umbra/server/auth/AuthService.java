package dev.umbra.server.auth;

import dev.umbra.server.auth.dto.LoginRequest;
import dev.umbra.server.auth.rto.LoginRto;
import dev.umbra.server.user.User;
import dev.umbra.server.user.UserRepository;
import dev.umbra.server.user.UserService;
import dev.umbra.server.user.dto.RegisterUserRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserService userService,
                        UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User register(RegisterUserRequest registerUserRequest) {
        return  userService.registerUser(registerUserRequest);
    }

    public LoginRto login(LoginRequest request) {
        String email = request.email().toLowerCase().trim();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        var isValidPassword = passwordEncoder.matches(request.password(), user.getPasswordHash());
        if (!isValidPassword) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getId(), user.getEmail());

        return new LoginRto(
                token,
                "Bearer",
                jwtService.getExpirationSeconds()
        );
    }
}
