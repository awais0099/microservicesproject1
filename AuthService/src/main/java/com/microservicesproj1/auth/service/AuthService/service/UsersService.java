package com.microservicesproj1.auth.service.AuthService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.microservicesproj1.auth.service.AuthService.entity.Users;
import com.microservicesproj1.auth.service.AuthService.repository.UsersRepository;

@Service
public class UsersService {
	
	@Autowired
	private UsersRepository usersRepository;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
	
	public Users createUser(Users user) {
		System.out.println("* UsersService > createUser");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return usersRepository.save(user);
	}
	
	public List<Users> getAllUsers() {
		return usersRepository.findAll();
	}
}
