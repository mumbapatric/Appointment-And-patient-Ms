package project.Appointment.And.Patient.MS.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private static  final  String SECRETE_kEY = "your-very-long-secret-key-that-is-secure-and-256-bits-long";

    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims (String token){
        System.out.println("extract all claims from token" + token);
        if (token== null || token.isEmpty() || !token.contains(".")){
            System.out.println("Invalid token format" + token);
            throw new IllegalArgumentException("Invalid token format");
        }
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRETE_kEY.getBytes());
    }

    public String generateToken(UserDetails userDetails, Long userId){
        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("userId",userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000* 60 * 60 * 10))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        System.out.println("Generated JWT:" + token);
        return token;
    }
    public Long extractUserId(String token){
        return extractClaim(token,claims -> claims.get("userId", Long.class));
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = extractUsername(token);
        System.out.println("Extracted username" + username);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

}
