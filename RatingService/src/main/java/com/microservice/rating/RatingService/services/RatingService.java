package com.microservice.rating.RatingService.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.microservice.rating.RatingService.entities.Rating;

public interface RatingService {
	public Rating create(Rating rating);
	public List<Rating> getAllRatings();
	public Rating getRating(String id);
	public List<Rating> getRatingByUserId(String userId);
	public List<Rating> getRatingByHotelId(String hotelId);
}
