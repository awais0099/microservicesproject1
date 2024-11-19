package com.microservices.apigateway.ApiGateWay.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.microservices.apigateway.ApiGateWay.dto.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(AuthServiceCustomException.class)
    public ResponseEntity<ApiResponse<String>> handleAuthServiceCustomException(AuthServiceCustomException ex) {
		ApiResponse<String> response = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), false,
				"Authentication failed", ex.getMessage(), new Date());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getResponseDetails());
    }
}
