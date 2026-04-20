package library.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import library.com.exceptions.UserNotFoundException;
import library.com.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	UserRepository rep;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return rep.findByLogin(username).orElseThrow(()-> new UserNotFoundException("user not found!"));
	}

}
