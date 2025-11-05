package org.eclipse.rest;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
	
	private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	private static final long EXP_MS = 2 * 30 * 60 * 1000; // 30 minutes
	
	public static String generateToken(String username, String role) {
		long now = System.currentTimeMillis();
		return Jwts.builder()
				.setSubject(username)
				.claim(username, role)
				.setIssuedAt(new Date(now))
				.setExpiration(new Date(now+EXP_MS))
				.signWith(KEY)
				.compact();
	}
	
	public static Claims parseToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(KEY)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}
