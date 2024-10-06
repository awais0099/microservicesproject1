package com.user.service.UserService.services.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.service.UserService.entities.Hotel;
import com.user.service.UserService.entities.Rating;
import com.user.service.UserService.entities.User;
import com.user.service.UserService.exceptions.ResourceNotFoundException;
import com.user.service.UserService.externalservices.HotelServiceFeignClient;
import com.user.service.UserService.repositories.UserRepository;
import com.user.service.UserService.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelServiceFeignClient hotelServiceFeignClient;

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
		logger.info("UserServiceImpl > getUser ");
		String rating_url = "http://RATINGSERVICE/ratings/user/";
		String hotel_url = "http://HOTELSERVICE/hotels/";
		
		User user = userRepository.findById(userId).orElseThrow(
				() -> new ResourceNotFoundException("User with the given id is not found in the database !!" + userId));

		// fetch ratings of the given user from Rating Service
		ResponseEntity<List<Rating>> rating_response = restTemplate.exchange(rating_url + user.getUserId(),
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Rating>>() {
				});
		List<Rating> ratings = rating_response.getBody();

		logger.info("ratings: {}", ratings.toString());
		
		List<Rating> ratingList = ratings.stream().map(rating -> {
//			ResponseEntity<Hotel> hotel_response = restTemplate.exchange(hotel_url + rating.getHotelId(),
//					HttpMethod.GET, null, Hotel.class);
//			rating.setHotel(hotel_response.getBody());
			Hotel hotel = hotelServiceFeignClient.getHotel(rating.getHotelId());
			rating.setHotel(hotel);
			return rating;
		}).collect(Collectors.toList());

		user.setRatings(ratingList);
		return user;
	}

}
