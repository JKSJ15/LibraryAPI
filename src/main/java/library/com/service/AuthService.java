package library.com.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import library.com.dto.LoginDto;
import library.com.dto.RegisterDto;
import library.com.entity.User;
import library.com.exceptions.UserAlreadyExistsException;
import library.com.repository.UserRepository;

@Service
public class AuthService {
	private final UserRepository rep;
	private AuthenticationManager authMan;
	@Autowired
	private PasswordEncoder encoder;
	
	public AuthService(UserRepository rep, PasswordEncoder encoder, AuthenticationManager authMan) {
		super();
		this.rep = rep;
		this.encoder = encoder;
		this.authMan = authMan;
	}
	
	public void registerNewUser(RegisterDto login) {
		Optional<User> userOptional = rep.findByLogin(login.login());
		if (userOptional.isPresent()) {throw new UserAlreadyExistsException("User already exists!");}
		String encryptedPass = encoder.encode(login.password());
		User newUser = new User(login.login(), encryptedPass, login.role());
		rep.save(newUser);
	}
	public void login(LoginDto login) {
	    var authToken = new UsernamePasswordAuthenticationToken(
	        login.login(),
	        login.password()
	    );
	    authMan.authenticate(authToken);
	}
	
}
