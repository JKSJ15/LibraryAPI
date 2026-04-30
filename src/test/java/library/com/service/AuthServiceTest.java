package library.com.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import library.com.configurations.JwtService;
import library.com.dto.LoginDto;
import library.com.dto.RegisterDto;
import library.com.entity.User;
import library.com.entity.UserRole;
import library.com.exceptions.UserAlreadyExistsException;
import library.com.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository rep;

    @Mock
    private AuthenticationManager authMan;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtService jwt;

    private RegisterDto registerDto;
    private LoginDto loginDto;
    private User user;

    @BeforeEach
    void setUp() {
        registerDto = new RegisterDto("test@email.com", "123456", UserRole.ROLE_USER);
        loginDto = new LoginDto("test@email.com", "123456");

        user = new User("test@email.com", "encrypted", UserRole.ROLE_USER);
    }

    // ================= REGISTER =================

    @Test
    void registerNewUser_shouldSaveUser_whenUserDoesNotExist() {
        Mockito.when(rep.findByLogin(registerDto.login()))
                .thenReturn(Optional.empty());

        Mockito.when(encoder.encode(registerDto.password()))
                .thenReturn("encrypted");

        authService.registerNewUser(registerDto);

        Mockito.verify(rep).save(Mockito.any(User.class));
    }

    @Test
    void registerNewUser_shouldThrowException_whenUserAlreadyExists() {
        Mockito.when(rep.findByLogin(registerDto.login()))
                .thenReturn(Optional.of(user));

        assertThatThrownBy(() -> authService.registerNewUser(registerDto))
                .isInstanceOf(UserAlreadyExistsException.class);
    }

    // ================= LOGIN =================

    @Test
    void login_shouldReturnToken_whenCredentialsAreValid() {
        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authMan.authenticate(Mockito.any()))
                .thenReturn(authentication);

        Mockito.when(authentication.getPrincipal())
                .thenReturn(user);

        Mockito.when(jwt.generateToken(user))
                .thenReturn("fake-jwt-token");

        String token = authService.login(loginDto);

        assertThat(token).isEqualTo("fake-jwt-token");
    }

    @Test
    void login_shouldCallAuthenticationManager() {
        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authMan.authenticate(Mockito.any()))
                .thenReturn(authentication);

        Mockito.when(authentication.getPrincipal())
                .thenReturn(user);

        Mockito.when(jwt.generateToken(Mockito.any()))
                .thenReturn("token");

        authService.login(loginDto);

        Mockito.verify(authMan).authenticate(Mockito.any());
    }
}
