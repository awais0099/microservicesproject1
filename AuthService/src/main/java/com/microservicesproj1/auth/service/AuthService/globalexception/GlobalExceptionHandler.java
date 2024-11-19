package com.microservicesproj1.auth.service.AuthService.globalexception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.microservicesproj1.auth.service.AuthService.dto.ApiResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Handle authentication failures
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiResponse<String>> handleBadCredentialsException(BadCredentialsException ex) {
		ApiResponse<String> response = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), false,
				ex.getMessage(), "", new Date());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ApiResponse<String>> handleExpiredJwtException(ExpiredJwtException ex) {
		ApiResponse<String> response = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), false,
				ex.getMessage(), "", new Date());
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}
	
	@ExceptionHandler(SignatureException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidSignatureException(SignatureException ex) {
        ApiResponse<String> response = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), false,
        		ex.getMessage(), "", new Date());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
	
	@ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ApiResponse<String>> handleMalformedJwtException(MalformedJwtException ex) {
        ApiResponse<String> response = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), false,
        		ex.getMessage(), "", new Date());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
	
	// Catch all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneralException(Exception ex) {
        ApiResponse<String> response = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), false,
        		ex.getMessage(), "", new Date());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
