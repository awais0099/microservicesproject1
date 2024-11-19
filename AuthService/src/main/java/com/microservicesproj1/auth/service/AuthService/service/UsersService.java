package com.microservicesproj1.auth.service.AuthService.service;

import java.util.List;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.microservicesproj1.auth.service.AuthService.entity.Users;
import com.microservicesproj1.auth.service.AuthService.repository.UsersRepository;

@Service
public class UsersService {
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtService jwtService;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
	
	public Users createUser(Users user) {
		System.out.println("* UsersService > createUser");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return usersRepository.save(user);
	}
	
	public List<Users> getAllUsers() {
		return usersRepository.findAll();
	}
	
	public String verify(Users user) {
		System.out.println("* UsersService > verify");
		try {
	        Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
	        );
	        return jwtService.generateToken(user.getUsername());
	    } catch (BadCredentialsException e) {
	        System.out.println("Invalid username or password.");  // For debugging
	        throw new BadCredentialsException("Invalid username or password.");
	    }
	}
}
