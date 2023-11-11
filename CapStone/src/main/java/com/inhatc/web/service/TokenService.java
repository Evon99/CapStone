package com.inhatc.web.service;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.inhatc.web.entity.Token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

    // Replace this with a secure secret key
    private static final String SECRET_KEY = "your-secure-secret-key";

    private Key secretKey;

    public TokenService() {
        init();
    }

    protected void init() {
        // Use a secure key generation strategy
        secretKey = new SecretKeySpec(generateSecureKey(), SignatureAlgorithm.HS256.getJcaName());
    }

    private byte[] generateSecureKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32];
        secureRandom.nextBytes(key);
        return key;
    }

    public Token generateToken(String uid, String role) {
        long tokenPeriod = 1000L * 60L * 10L;
        long refreshPeriod = 1000L * 60L * 60L * 24L * 30L * 3L;

        Claims claims = Jwts.claims().setSubject(uid);
        claims.put("role", role);

        Date now = new Date();
        return new Token(
                Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(new Date(now.getTime() + tokenPeriod))
                    .signWith(secretKey)
                    .compact(),
                Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(new Date(now.getTime() + refreshPeriod))
                    .signWith(secretKey)
                    .compact());
    }

    public boolean verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return claims.getBody().getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getUid(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }
}
