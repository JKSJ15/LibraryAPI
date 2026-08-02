package library.com.controller;

import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import library.com.dto.LoginDto;
import library.com.dto.LoginResponseDto;
import library.com.dto.RegisterDto;
import library.com.dto.RefreshRequestDto;
import library.com.entity.User;
import library.com.exceptions.UserNotFoundException;
import library.com.repository.UserRepository;
import library.com.service.AuthService;
import library.com.service.RefreshTokenService;

@Tag(name = "Authentication")
@RestController
@RequestMapping("/auth")
public class AuthController {
	private final AuthService service;
	private final RefreshTokenService refreshTokenService;
	private final UserRepository userRep;
	
	public AuthController(AuthService service, RefreshTokenService refreshTokenService, UserRepository userRep) {
		super();
		this.service = service;
		this.refreshTokenService = refreshTokenService;
		this.userRep = userRep;
	}
	
	@Operation(summary = "Register a new user")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data or user already exists")
		})
	@PostMapping("/register")
	public ResponseEntity<Void> register(@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "User registration data") @RequestBody RegisterDto login) {
		service.registerNewUser(login);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@Operation(summary = "Authenticate user", description = "Returns a JWT Bearer access token and refresh token. Use the access token in the Authorization header as: Bearer <token>")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Authentication successful"),
        @ApiResponse(responseCode = "403", description = "Invalid credentials")
		})
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "User credentials")
	@RequestBody LoginDto login) {
		return ResponseEntity.ok(service.login(login));
	}

	@Operation(summary = "Refresh access token", description = "Returns a new JWT Bearer access token and refresh Token.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
		@ApiResponse(responseCode = "401", description = "Invalid refresh token")
		})
	@PostMapping("/refresh")
	public ResponseEntity<LoginResponseDto> refreshToken(@RequestBody RefreshRequestDto request) {
		return ResponseEntity.ok(service.refreshToken(request));
	}

	@Operation(summary = "Logout", description = "Invalidates the refresh token for the authenticated user.")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Logout successful"),
		@ApiResponse(responseCode = "401", description = "Unauthorized")
		})
	@PostMapping("/logout")
	@Transactional
	public ResponseEntity<Void> logout(Authentication authentication) {
		String login = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRep.findByLogin(login).orElseThrow(() -> new UserNotFoundException("User not authenticated!")); //deveria ser unauthorized
		refreshTokenService.deleteByUser(user);
		return ResponseEntity.noContent().build();
	}
}
