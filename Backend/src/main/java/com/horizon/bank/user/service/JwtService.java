package com.horizon.bank.user.service;

import java.util.Date;
import java.util.HashMap;

import javax.crypto.SecretKey;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final String key;
    private final Environment env;

    public JwtService(Environment env) {
        this.env = env;
        this.key = env.getProperty("JWT_SIGNING_KEY");
    }

    public HashMap<String, String> generateToken(String email) {
        byte[] keyBytes = key.getBytes();
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);
        String accessToken = Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(secretKey)
                .compact();
        // String refreshToken = Jwts.builder()
        //         .subject(email)
        //         .issuedAt(new Date())
        //         .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
        //         .signWith(secretKey)
        //         .compact();
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        // tokens.put("refreshToken", refreshToken);
        return tokens;
    }
}
