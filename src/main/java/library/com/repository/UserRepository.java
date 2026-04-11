package library.com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import library.com.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByLogin(String login);
}
