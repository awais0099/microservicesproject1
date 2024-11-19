package com.microservices.apigateway.ApiGateWay.authenticationfilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.apigateway.ApiGateWay.dto.ApiResponse;
import com.microservices.apigateway.ApiGateWay.exception.AuthServiceCustomException;
import com.microservices.apigateway.ApiGateWay.serviceclient.AuthServiceClient;

import reactor.core.publisher.Mono;

@Component("Jwt")
public class JwtGatewayFilterFactory extends AbstractGatewayFilterFactory<JwtGatewayFilterFactory.Config> {
	private static final Logger logger = LoggerFactory.getLogger(JwtGatewayFilterFactory.class);
	
	@Autowired
    private AuthServiceClient authServiceClient;
	
	private static final List<String> allowedUrls = new ArrayList<>();

    static {
        allowedUrls.add("/auth/login");
        allowedUrls.add("/auth/register");
        allowedUrls.add("/auth/test");
        allowedUrls.add("/auth/validate");
    }
	
	public JwtGatewayFilterFactory() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
	    return (exchange, chain) -> {
	        logger.info("* JwtGatewayFilterFactory: Filter applied");
	        String path = exchange.getRequest().getURI().getPath();

	        // Allow requests to specific paths without authentication
	        if (allowedUrls.contains(path)) {
	            return chain.filter(exchange);
	        }
	        
	        String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
	        
	        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
	            logger.warn("Missing or invalid Authorization header");
	            return handleUnauthorizedResponse(exchange, "Authorization header is missing or invalid");
	        }

	        String token = authorizationHeader.substring(7);
	        return authServiceClient.validateToken("test5", token)
	        		.flatMap(response -> {
	        			ApiResponse<String> authres = response;
	        			
	        			// Check if authentication failed
	        			if (authres.getStatus() == 401) {
	        				throw new AuthServiceCustomException("Authentication failed", authres);
	        			}
	        			
	        			logger.info("* response: {}", authres.getMessage());
	        			return chain.filter(exchange);
	        		});
	    };
	}


	public Mono<Void> handleUnauthorizedResponse(ServerWebExchange exchange, String messageh) {
		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);	
		
		Map<String, Object> errorDetails = new HashMap<>();
		errorDetails.put("data", "");
		errorDetails.put("message", messageh);
		errorDetails.put("success", false);
		errorDetails.put("status", 401);
		
		ObjectMapper objectMapper = new ObjectMapper();	
		byte[] bytes;
		try {
			bytes = objectMapper.writeValueAsBytes(errorDetails);
		} catch (JsonProcessingException e) {
			e.printStackTrace(); // Or handle it with custom logging
	        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
	        return exchange.getResponse().setComplete();
		}

		DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
		return exchange.getResponse().writeWith(Mono.just(buffer));
	}

	public static class Config {
		// Define any configuration properties here
	}
}
