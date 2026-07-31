package library.com.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class RefreshToken{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "token", nullable = false, unique = true)
    private String token;
    @Column(name = "expiration_date", nullable = false)
    private Instant expirationDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    
    public RefreshToken() {
    }
    public RefreshToken(String token, Instant expirationDate, User user) {
        this.token = token;
        this.expirationDate = expirationDate;
        this.user = user;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expirationDate);
    }
     public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Instant getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }
}
