package library.com.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import library.com.configurations.JwtService;
import library.com.dto.LoginDto;
import library.com.dto.RegisterDto;
import library.com.entity.User;
import library.com.exceptions.UserAlreadyExistsException;
import library.com.repository.UserRepository;

@Service
public class AuthService {
	private final UserRepository rep;
	private final AuthenticationManager authMan;
	@Autowired
	private PasswordEncoder encoder;
	private final JwtService jwt;
	
	
	public AuthService(UserRepository rep, AuthenticationManager authMan, PasswordEncoder encoder, JwtService jwt) {
		super();
		this.rep = rep;
		this.authMan = authMan;
		this.encoder = encoder;
		this.jwt = jwt;
	}

	@PreAuthorize("permitAll()")
	public void registerNewUser(RegisterDto login) {
		Optional<User> userOptional = rep.findByLogin(login.login());
		if (userOptional.isPresent()) {throw new UserAlreadyExistsException("User already exists!");}
		String encryptedPass = encoder.encode(login.password());
		User newUser = new User(login.login(), encryptedPass, login.role());
		rep.save(newUser);
		}
	
	@PreAuthorize("permitAll()")
	public String login(LoginDto login) {
	    var authToken = new UsernamePasswordAuthenticationToken(
	            login.login(),
	            login.password()
	    );
	    var authentication = authMan.authenticate(authToken);
	    User user = (User) authentication.getPrincipal();

	    return jwt.generateToken(user);
	}
	
}
