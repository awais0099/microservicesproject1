package com.microservices.apigateway.ApiGateWay.exception;

import com.microservices.apigateway.ApiGateWay.dto.ApiResponse;

public class AuthServiceCustomException extends RuntimeException {
	private ApiResponse<String> authResponse;

    public AuthServiceCustomException(String message, ApiResponse<String> response) {
        super(message);
        this.authResponse = response;
    }
    
    public ApiResponse<String> getResponseDetails() {
        return authResponse;
    }
}
