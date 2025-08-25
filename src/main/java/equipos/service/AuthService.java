package equipos.service;

import equipos.exception.AuthException;
import equipos.model.Auth;
import equipos.model.dto.auth.LoginRequestDTO;
import equipos.model.dto.auth.LoginResponseDTO;
import equipos.repository.AuthRepository;
import equipos.security.JWTUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private PasswordEncoder passwordEncoder;
    private JWTUtils jwtUtils;

    public AuthService(AuthRepository authRepository, PasswordEncoder passwordEncoder, JWTUtils jwtUtils) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        Auth user = authRepository.findByUsername(loginRequest.username());

        if (user == null || !passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new AuthException();
        }
        return new LoginResponseDTO(jwtUtils.generateToken(user.getUsername()));
    }
}
