package library.com.configurations;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import library.com.repository.UserRepository;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	private final JwtService jwtService;
    private final UserRepository rep;

    public SecurityFilter(JwtService jwtService, UserRepository rep) {
        this.jwtService = jwtService;
        this.rep = rep;
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var token = this.recoverToken(request);
		if (token != null) {
		    var login = jwtService.validateToken(token);

		    if (login != null) {
		        UserDetails user = rep.findByLogin(login).orElseThrow();

		        var authentication = new UsernamePasswordAuthenticationToken(
		                user, null, user.getAuthorities());

		        SecurityContextHolder.getContext().setAuthentication(authentication);
		    }
		}
		filterChain.doFilter(request, response);
		
	}
	private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        return authHeader.replace("Bearer ", "");
    }
	
}
