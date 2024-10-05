package com.user.service.UserService.services.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.service.UserService.entities.Rating;
import com.user.service.UserService.entities.User;
import com.user.service.UserService.exceptions.ResourceNotFoundException;
import com.user.service.UserService.repositories.UserRepository;
import com.user.service.UserService.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with the given id is not found in the database !!" + userId));
		
		// fetch ratings of the given user from Rating Service
		ArrayList<Rating> ratings = restTemplate.getForObject("http://localhost:8083/ratings/user/"+user.getUserId(), ArrayList.class);
		logger.info("get single user");
//		logger.info("{} ", ratings);
		user.setRatings(ratings);
		return user;
	}

}
