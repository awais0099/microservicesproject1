package com.microservicesproj1.auth.service.AuthService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicesproj1.auth.service.AuthService.entity.Users;
import com.microservicesproj1.auth.service.AuthService.service.UsersService;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	private UsersService usersService;
	
	@GetMapping
	public List<Users> users() {
		return usersService.getAllUsers();
	}
	
	@PostMapping("/register")
	public Users register(@RequestBody Users user) {
		System.out.println("* UsersController > register");
		
		Users createdUser = usersService.createUser(user);
		return createdUser;
	}
}
