package br.com.biblioteca.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationMs;

   
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

 
    public String generateToken(String subject) {
        return generateToken(subject, new HashMap<>());
    }

    
    public String generateToken(String subject, Map<String, Object> extraClaims) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .claims(extraClaims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(getSigningKey())
                .compact();
    }

    
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

   
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

   
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

 
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

  
    public boolean validateToken(String token, String expectedEmail) {
        try {
            String email = extractEmail(token);
            boolean notExpired = !isTokenExpired(token);
            boolean emailMatch = email.equals(expectedEmail);

            if (!emailMatch) {
                log.warn("JWT email mismatch. Expected: {}, Got: {}", expectedEmail, email);
            }
            if (!notExpired) {
                log.warn("JWT token expired for email: {}", email);
            }

            return emailMatch && notExpired;
        } catch (JwtException e) {
            log.error("JWT validation error: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("Unexpected error during JWT validation: {}", e.getMessage());
            return false;
        }
    }

    public boolean validateToken(String token) {
        try {
            String email = extractEmail(token);
            return validateToken(token, email);
        } catch (Exception e) {
            return false;
        }
    }


   
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}