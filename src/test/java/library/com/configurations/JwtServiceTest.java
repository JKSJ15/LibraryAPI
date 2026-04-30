package library.com.configurations;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import library.com.entity.User;

public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        try {
            var field = JwtService.class.getDeclaredField("secret");
            field.setAccessible(true);
            field.set(jwtService, "test-secret-key");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void generateToken_shouldReturnValidToken() {
        User user = new User();
        user.setEmail("test@email.com");

        String token = jwtService.generateToken(user);

        assertThat(token).isNotNull();
        assertThat(token).isNotBlank();
    }

    @Test
    void validateToken_shouldReturnSubject_whenTokenIsValid() {
        User user = new User();
        user.setEmail("test@email.com");

        String token = jwtService.generateToken(user);
        String subject = jwtService.validateToken(token);

        assertThat(subject).isEqualTo(user.getLogin());
    }

    @Test
    void validateToken_shouldReturnNull_whenTokenIsInvalid() {
        String invalidToken = "token.invalido.qualquer";

        String subject = jwtService.validateToken(invalidToken);

        assertThat(subject).isNull();
    }

    @Test
    void validateToken_shouldReturnNull_whenTokenIsTampered() {
        User user = new User();
        user.setEmail("test@email.com");

        String token = jwtService.generateToken(user);
        String tamperedToken = token + "abc";
        String subject = jwtService.validateToken(tamperedToken);

        assertThat(subject).isNull();
    }
}
