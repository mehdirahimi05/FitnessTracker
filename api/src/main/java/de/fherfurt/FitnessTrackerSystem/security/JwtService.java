package de.fherfurt.FitnessTrackerSystem.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public boolean isTokenValid(String token) {
        try {
            extractUserName(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(String userName) {
        return Jwts.builder()           // start building a token
                .subject(userName)      // throw the userName in "mehdi"
                .issuedAt(new Date())   // when was the token created "now"
                .expiration(new Date(   // expiration now + 24 hours (expirationTime)
                        System.currentTimeMillis()
                                +
                                expirationTime)
                )
                .signWith(getSigningKey())  // sign with SecretKey
                .compact();             // build the final token
    }

    public String extractUserName(String token) {
        return Jwts.parser()                       // start reading the token
                .verifyWith(getSigningKey())       // verify with getSigningKey
                .build()
                .parseSignedClaims(token)          // read the token
                .getPayload()                      // call the token
                .getSubject();                      //call the userName "mehdi"
    }

}
