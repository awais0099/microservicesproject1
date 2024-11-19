package com.microservicesproj1.auth.service.AuthService.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Service
public class JwtService {

	private String mysecretkey;

	public JwtService() {
		Dotenv dotenv = Dotenv.configure().directory(".") // Specify the directory containing the .env file
				.filename(".env") // Specify the filename
				.load();
		mysecretkey = dotenv.get("JWT_SECRET_KEY");
	}

	public String generateToken(String username) {
		System.out.println("generateToken ******");

		Map<String, Object> claims = new HashMap<>();

		String jwt = Jwts.builder().claims().add(claims).subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2)).and().signWith(getKey())
				.compact();

		return jwt;
	}

	private SecretKey getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(mysecretkey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	private Claims extractAllClaims(String token) {
		String message = "";
		try {
			return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            message = "Token has expired: " + e.getMessage();
            throw new ExpiredJwtException(null, null, message);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | SecurityException e) {
            message = "Invalid token: " + e.getMessage();
            throw new SignatureException(message);
        } catch (Exception e) {
            message = "An error occurred: " + e.getMessage();
            throw new MalformedJwtException(message);
        }
	}

	public Boolean isTokenExpired(String token) {
		return extractAllClaims(token).getExpiration().before(new Date());
	}

	public Boolean validateToken(String token, String username) {
		System.out.println("* JwtService > validateToken");
		
		final String token_username = extractUsername(token);
		return (token_username.equals(username) && !isTokenExpired(token));
	}
}
