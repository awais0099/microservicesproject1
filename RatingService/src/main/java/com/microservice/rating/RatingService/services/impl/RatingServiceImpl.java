package com.microservice.rating.RatingService.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.rating.RatingService.entities.Rating;
import com.microservice.rating.RatingService.repositories.RatingRepository;
import com.microservice.rating.RatingService.services.RatingService;

@Service
public class RatingServiceImpl implements RatingService {
	
	@Autowired
	private RatingRepository ratingRepository;
	
	@Override
	public Rating create(Rating rating) {
		String randomId = UUID.randomUUID().toString();
		rating.setRatingId(randomId);
		Rating newRating = ratingRepository.save(rating);
		return newRating;
	}

	@Override
	public List<Rating> getAllRatings() {
		return ratingRepository.findAll();
	}

	@Override
	public Rating getRating(String id) {
		return ratingRepository.findById(id).orElseThrow();
	}

	@Override
	public List<Rating> getRatingByUserId(String userId) {
		System.out.println("getRatingByUserId Serv *");
		return ratingRepository.findByUserId(userId);
	}

	@Override
	public List<Rating> getRatingByHotelId(String hotelId) {
		return ratingRepository.findByHotelId(hotelId);
	}
}
