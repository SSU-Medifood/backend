package Mefo.server.global.login.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secret-key}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); // ✅ Use Keys.hmacShaKeyFor()
    }

    public String getLoginId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey) // ✅ Use parserBuilder().setSigningKey()
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("loginId", String.class);
    }

    public String getRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    public String createJwt(String loginId, String role, Long expiredMs) {
        return Jwts.builder()
                .claim("loginId", loginId)
                .claim("role", role)
                .setIssuedAt(new Date(System.currentTimeMillis())) // ✅ Use setIssuedAt()
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs)) // ✅ Use setExpiration()
                .signWith(secretKey, SignatureAlgorithm.HS256) // ✅ Explicitly set SignatureAlgorithm
                .compact();
    }
}
