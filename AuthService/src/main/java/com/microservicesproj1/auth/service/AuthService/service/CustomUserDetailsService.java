package com.microservicesproj1.auth.service.AuthService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.microservicesproj1.auth.service.AuthService.entity.CustomUserDetails;
import com.microservicesproj1.auth.service.AuthService.entity.Users;
import com.microservicesproj1.auth.service.AuthService.repository.UsersRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UsersRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = repo.findByUsername(username);
		
		if (user == null) {
			System.out.println("User Not Found");
			throw new UsernameNotFoundException("User Not Found");
		}
		
		return new CustomUserDetails(user);
	}

}
