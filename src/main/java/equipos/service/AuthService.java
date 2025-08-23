package equipos.service;

import equipos.exception.AuthException;
import equipos.model.Auth;
import equipos.model.dto.auth.LoginRequestDTO;
import equipos.model.dto.auth.LoginResponseDTO;
import equipos.repository.AuthRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        Auth user = authRepository.findByUsername(loginRequest.username());

        if (!user.getPassword().equals(loginRequest.password())) {
            throw new AuthException();
        }
        //TODO: Devolver el token
        return new LoginResponseDTO(user.getUsername());
    }
}
