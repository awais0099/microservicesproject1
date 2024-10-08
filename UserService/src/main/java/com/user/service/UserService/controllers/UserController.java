package com.user.service.UserService.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.service.UserService.entities.User;
import com.user.service.UserService.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User new_user = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(new_user);
	}
	
	@GetMapping("/users/{userId}")
	@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelBreakerFallback")
	@RateLimiter(name = "userRatingLimiter", fallbackMethod = "ratingHotelBreakerFallback")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId) {
		User user = userService.getUser(userId);
		return ResponseEntity.ok(user);
	}
	
	public ResponseEntity<User> ratingHotelBreakerFallback(String userId, Exception ex) {
		logger.info("Fallback is executed because service is down: ", ex.getMessage());
		
		User user = User
				.builder()
				.email("dummy@dummy.com")
				.name("dummy")
				.about("This dummay user is created because some other required service is down")
				.userId("0000")
				.build();
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUser() {
		List<User> users = userService.getAllUser();
		return ResponseEntity.ok(users);
	}
}
