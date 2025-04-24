package project.Appointment.And.Patient.MS.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service

public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    public String extractUsername(String token) {
        validateToken(token);
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        validateToken(token); // Check token before resolving claims
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        validateTokenFormat(token); // Ensure the format is valid
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to extract claims: Token invalid or improperly signed.", e);
        }
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(UserDetails userDetails, Long userId, String name) {
        List<String> roles = userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());

        String tokenId = UUID.randomUUID().toString(); // Unique token ID
        return Jwts.builder()
                .setId(tokenId)
                .setSubject(userDetails.getUsername())
                .claim("userId", userId)
                .claim("name", name)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Long extractUserId(String token) {
        validateToken(token); // Check token before extracting UserId
        return extractClaim(token, claims -> claims.get("userId", Long.class));
    }

    public List<String> extractRoles(String token) {
        validateToken(token); // Check token before extracting Roles
        return extractClaim(token, claims -> claims.get("roles", List.class));
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (Exception e) {
            return false; // If any validation or extraction fails, the token is invalid
        }
    }

    private boolean isTokenExpired(String token) {
        validateToken(token); // Ensure token is valid before checking expiry
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        validateToken(token); // Ensure token is valid before extracting Expiration
        return extractClaim(token, Claims::getExpiration);
    }

    // New Method: Validate token format
    private void validateTokenFormat(String token) {
        if (token == null || token.isEmpty() || !token.contains(".")) {
            throw new IllegalArgumentException("Invalid token format");
        }
    }

    // New Method: General token validation
    private void validateToken(String token) {
        validateTokenFormat(token); // Check structure first
        try {
            // Parse claims to ensure token validity (like proper signature)
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid or expired token", e);
        }
    }
}