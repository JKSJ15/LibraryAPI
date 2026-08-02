package library.com.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import library.com.exceptions.UnauthorizedException;
import library.com.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	private final UserRepository rep;
	public CustomUserDetailsService(UserRepository rep) {
		this.rep = rep;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UnauthorizedException {
		return rep.findByLogin(username).orElseThrow(() -> new UnauthorizedException("invalid username"));
	}

}
