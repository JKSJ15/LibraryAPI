package library.com.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import library.com.entity.RefreshToken;
import library.com.entity.User;
import library.com.exceptions.InvalidRefreshTokenException;
import library.com.exceptions.InvalidTokenException;
import library.com.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(String token, User user) {
        RefreshToken refreshToken = new RefreshToken(token, LocalDateTime.now().plusDays(3).toInstant(ZoneOffset.of("-03:00")), user);
        return refreshTokenRepository.save(refreshToken);
    }
    public boolean isRefreshTokenValid(String refreshToken) {
        RefreshToken token = getRefreshToken(refreshToken);
        if (token.isExpired()){
            return false;
        }
        return true;
    }
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }
    public void deleteByToken(String token) {
        RefreshToken refreshToken = getRefreshToken(token);
        refreshTokenRepository.delete(refreshToken);
    }
    private RefreshToken getRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidRefreshTokenException("Invalid Refresh Token!"));
    }
}
