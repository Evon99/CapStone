package com.inhatc.web.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {

	 @Value("${jasypt.encryptor.secretKey}") //application.properties에 저장되어 있는 값을 가져온다.
	 private String secretKey;
	 
	 @Value("${jwt.expiredMs}") //application.properties에 저장되어 있는 값을 가져온다.
	 private Long expiredMs;
	    
	public static String getMemberEmail(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("email", String.class);
    }
	
    public static boolean isExpired(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }
	
	public String createJwt(String memberEmail, Long id) {
        Claims claims = Jwts.claims();
        claims.put("email", memberEmail);
        claims.put("id", id);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return token;
    }
	
}
