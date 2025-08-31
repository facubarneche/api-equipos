package equipos;

import equipos.exception.AuthException;
import equipos.model.Auth;
import equipos.model.dto.auth.LoginRequestDTO;
import equipos.model.dto.auth.LoginResponseDTO;
import equipos.repository.AuthRepository;
import equipos.security.JWTUtils;
import equipos.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthRepository authRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTUtils jwtUtils;

    @InjectMocks
    private AuthService authService;

    private String username = "test";

    @Test
    void loginTest() {
        String rawPassword = "12345";
        String encodedPassword = "encodedPass";
        String token = "jwtToken";
        Auth user = new Auth();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        LoginRequestDTO loginRequest = new LoginRequestDTO(username, rawPassword);
        when(authRepository.findByUsername(username)).thenReturn(user);
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
        when(jwtUtils.generateToken(username)).thenReturn(token);

        LoginResponseDTO response = authService.login(loginRequest);

        assertEquals(token, response.token());
        verify(authRepository).findByUsername(username);
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
        verify(jwtUtils).generateToken(username);
    }

    @Test
    void loginUserNullAuthExceptionTest() {
        String rawPassword = "wrongPassword";
        LoginRequestDTO loginRequest = new LoginRequestDTO(username, rawPassword);
        when(authRepository.findByUsername(username)).thenReturn(null);

        assertThrows(AuthException.class, () -> authService.login(loginRequest));

        verify(authRepository).findByUsername(username);
        verify(passwordEncoder, never()).matches(any(), any());
        verify(jwtUtils, never()).generateToken(any());
    }

    @Test
    void loginPasswordEncoderNotMatchesAuthExceptionTest() {
        String rawPassword = "wrongPassword";
        String encodedPassword = "encodedPass";
        Auth user = new Auth();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        LoginRequestDTO loginRequest = new LoginRequestDTO(username, rawPassword);
        when(authRepository.findByUsername(username)).thenReturn(user);
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        assertThrows(AuthException.class, () -> authService.login(loginRequest));
        verify(authRepository).findByUsername(username);
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
        verify(jwtUtils, never()).generateToken(any());
    }

}