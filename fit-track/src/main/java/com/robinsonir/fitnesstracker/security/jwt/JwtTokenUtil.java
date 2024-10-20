package com.robinsonir.fitnesstracker.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtTokenUtil {

    private static final String SECRET_KEY = "fitness_tracker_8702_tracker_fitness_2078";

    public String generateToken(String subject, String... roles) {
        return generateToken(subject, Map.of("scopes", roles));
    }

    public String generateToken(String subject, List<String> roles) {
        return generateToken(subject, Map.of("scopes", roles));
    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    public String generateToken(String subject, Map<String, Object> claims) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer("Robinsonir")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, String username) {
        final String subject = getSubject(token);
        return subject.equals(username) && !isTokenExpired(token);
    }

    private Claims getClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public boolean isTokenExpired(String token) {
        Date today = Date.from(Instant.now());
        return getClaims(token).getExpiration().before(today);
    }


    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

}
