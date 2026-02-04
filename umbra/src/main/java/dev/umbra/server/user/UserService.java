package dev.umbra.server.user;

import dev.umbra.server.user.dto.RegisterUserRequest;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerUser(RegisterUserRequest request) {
        String email = request.email().toLowerCase().trim();

        if(userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User already exists!");
        }

        String password = passwordEncoder.encode(request.password());
        User user = new User(request.email(), password, request.kdfType(), request.kdfIterations());

        return userRepository.save(user);
    }
}
