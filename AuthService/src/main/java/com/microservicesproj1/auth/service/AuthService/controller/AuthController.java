package com.microservicesproj1.auth.service.AuthService.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservicesproj1.auth.service.AuthService.dto.ApiResponse;
import com.microservicesproj1.auth.service.AuthService.dto.LoginResponse;
import com.microservicesproj1.auth.service.AuthService.dto.TokenValidationRequest;
import com.microservicesproj1.auth.service.AuthService.entity.Users;
import com.microservicesproj1.auth.service.AuthService.service.CustomUserDetailsService;
import com.microservicesproj1.auth.service.AuthService.service.JwtService;
import com.microservicesproj1.auth.service.AuthService.service.UsersService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UsersService usersService;
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
    private JwtService jwtService;

	@GetMapping("/test")
	public ResponseEntity<ApiResponse<String>> test() {		
//		return "testing";
		ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(), true, "Token validated", "Your token is validate", new Date());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/users")
	public ResponseEntity<ApiResponse<List<Users>>> getAllUsers() {
		List<Users> users = usersService.getAllUsers();
		ApiResponse<List<Users>> response = new ApiResponse<>(HttpStatus.OK.value(), true, "Retrieved users", users, new Date());
		return ResponseEntity.ok(response);
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody Users user) {
		String token = usersService.verify(user);
		LoginResponse loginResponse = new LoginResponse(token, "Login successful");
		ApiResponse<LoginResponse> response = new ApiResponse<>(HttpStatus.OK.value(), true, "User authenticated successfully", loginResponse, new Date());
		return ResponseEntity.ok(response);
	}

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<Users>> register(@RequestBody Users user) {
		System.out.println("* UsersController > register");
		Users createdUser = usersService.createUser(user);
		ApiResponse<Users> response = new ApiResponse<>(HttpStatus.CREATED.value(), true, "User registered successfully", createdUser, new Date());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PostMapping("/validate")
    public ResponseEntity<ApiResponse<String>> validateToken(@RequestBody TokenValidationRequest request) {
		System.out.println("* AuthController > validateToken");
		System.out.println("> username: " + request.getUsername());
		System.out.println("> token: " + request.getToken());
		
		boolean isExpired = jwtService.isTokenExpired(request.getToken());
		System.out.println("> isExpired: " + isExpired);
		
		ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(), isExpired, "Token validated", "Your token is validate", new Date());
		return ResponseEntity.ok(response);
    }
}
