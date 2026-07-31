package library.com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import library.com.entity.RefreshToken;
import library.com.entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{

    public Optional<RefreshToken> findByToken(String token);
    
    public void deleteByUser(User user);
}
