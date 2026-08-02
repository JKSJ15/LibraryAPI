package library.com.configurations;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import library.com.entity.User;

@Service
public class JwtService {
	@Value("${api.security.token.secret}")
	private String secret;
	
	public String generateAccessToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create()
					.withIssuer("library-api")
					.withSubject(user.getLogin())
					.withExpiresAt(generateExpirationDateAcessToken())
					.sign(algorithm);
			return token;
		} catch (JWTCreationException e) {
			throw new RuntimeException("Error while generation JWT token "+e);
		}
	}
	
	public String validateAccessToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("library-api")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException e) {
			return null;
		}
	}

	public String generateRefreshToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create()
					.withJWTId(UUID.randomUUID().toString())
					.withIssuer("library-api")
					.withSubject(user.getLogin())
					.withExpiresAt(generateExpirationDateRefreshToken())
					.sign(algorithm);
			return token;
		} catch (JWTCreationException e) {
			throw new RuntimeException("Error while generation JWT token "+e);
		}
	}
	
	public String validateRefreshToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("library-api")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException e) {
			throw new RuntimeException("Error while validating JWT token "+e);
		}
	}
	
	private Instant generateExpirationDateAcessToken() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
	private Instant generateExpirationDateRefreshToken() {
		return LocalDateTime.now().plusDays(3).toInstant(ZoneOffset.of("-03:00"));
	}
}
