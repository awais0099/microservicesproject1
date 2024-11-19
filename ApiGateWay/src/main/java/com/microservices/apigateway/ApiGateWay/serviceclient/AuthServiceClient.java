package com.microservices.apigateway.ApiGateWay.serviceclient;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.apigateway.ApiGateWay.dto.ApiResponse;
import com.microservices.apigateway.ApiGateWay.dto.TokenValidationRequest;

import reactor.core.publisher.Mono;

@Service
public class AuthServiceClient {

	private final WebClient.Builder webClientBuilder;
	
//	ApiResponse<String> auth_response = new ApiResponse<String>();

	@Autowired
	public AuthServiceClient(WebClient.Builder webClientBuilder) {
		this.webClientBuilder = webClientBuilder;
	}

	public Mono<ApiResponse<String>> validateToken(String username, String token) {
	    System.out.println("* AuthServiceClient > validateToken");
	    TokenValidationRequest requestData = new TokenValidationRequest(username, token);

	    return webClientBuilder
	            .build()
	            .post()
	            .uri("http://AUTHSERVICE/auth/validate")
	            .accept(MediaType.APPLICATION_JSON)
	            .bodyValue(requestData)
	            .exchangeToMono(res -> {
	                System.out.println("* exchangeToMono");
	                return res.toEntity(new ParameterizedTypeReference<ApiResponse<String>>() {});
	            }).map(ResponseEntity::getBody);
	}
}
