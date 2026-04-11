package library.com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import library.com.dto.LoginDto;
import library.com.dto.RegisterDto;
import library.com.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final AuthService service;
	
	public AuthController(AuthService service) {
		super();
		this.service = service;
	}
	
	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody RegisterDto login) {
		service.registerNewUser(login);
		return ResponseEntity.ok().build();
	}
	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody LoginDto login) {
		service.login(login);
		return ResponseEntity.ok().build();
	}
	
}
