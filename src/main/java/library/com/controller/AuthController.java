package library.com.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import library.com.dto.LoginDto;
import library.com.dto.RegisterDto;
import library.com.service.AuthService;

@Tag(name = "Authentication")
@RestController
@RequestMapping("/auth")
public class AuthController {
	private final AuthService service;
	
	public AuthController(AuthService service) {
		super();
		this.service = service;
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
	
	@Operation(summary = "Authenticate user", description = "Returns a JWT Bearer token. Use it in the Authorization header as: Bearer <token>")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Authentication successful"),
        @ApiResponse(responseCode = "403", description = "Invalid credentials")
		})
	@PostMapping("/login")
	public ResponseEntity<String> login(@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "User credentials")
	@RequestBody LoginDto login) {
		return ResponseEntity.ok(service.login(login));
	}
}
