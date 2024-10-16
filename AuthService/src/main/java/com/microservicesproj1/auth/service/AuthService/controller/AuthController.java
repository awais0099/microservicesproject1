package com.microservicesproj1.auth.service.AuthService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@GetMapping("/test")
	public String test() {
		return "testing";
	}
	
	@PostMapping("/register")
	public String saveUser() {
		return "testing";
	}
	
	@PostMapping("/validate")
	public String validate() {
		return "testing";
	}
	
	@PostMapping("/token")
	public String token() {
		return "testing";
	}
}
