package com.pruebatenica01.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtHelper {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private Long expiration;


    public String generateToken(String email,List<String> roles) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration * 1000);

        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expirationDate)
                .claim("roles", roles)
                //.claim(Map.of("roles", roles))
                .signWith(this.getSecretKey())
                .compact();
    }
    @SuppressWarnings("unchecked")
    public List<String> getRolesFromJwt(String token){
        Claims claims = this.getClaimsFromJwt(token);
        return (List<String>) claims.get("roles");
    }

    public String getUsernameFromJwt(String token){
        Claims claims = this.getClaimsFromJwt(token);
        return claims.getSubject();
    }

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    private Claims getClaimsFromJwt(String token) {
        return Jwts.parser()
                .verifyWith(this.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateJwt(String token) {
        try {
           final Claims claims = this.getClaimsFromJwt(token);
           final Date expirationDate = claims.getExpiration();
            return expirationDate.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }


}
