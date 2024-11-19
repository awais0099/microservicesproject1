package com.microservice.rating.RatingService.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.rating.RatingService.dto.ApiResponse;
import com.microservice.rating.RatingService.entities.Rating;
import com.microservice.rating.RatingService.services.RatingService;

@RestController
public class RatingController {
	
	@Autowired
	RatingService ratingService;
	
	@PostMapping("/ratings")
	public ResponseEntity<ApiResponse<Rating>> create(@RequestBody Rating rating) {
		Rating createdRating = ratingService.create(rating);
		ApiResponse<Rating> response = new ApiResponse<>(HttpStatus.OK.value(), true, "Created rating", rating, new Date());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/ratings")
	public ResponseEntity<ApiResponse<List<Rating>>> getAllRating() {
		List<Rating> ratings = ratingService.getAllRatings();
		ApiResponse<List<Rating>> response = new ApiResponse<>(HttpStatus.OK.value(), true, "Retrieved ratings", ratings, new Date());
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/ratings/{ratingId}")
	public ResponseEntity<ApiResponse<Rating>> getRating(@PathVariable String ratingId) {
		Rating rating = ratingService.getRating(ratingId);
		ApiResponse<Rating> response = new ApiResponse<>(HttpStatus.OK.value(), true, "Retrieved rating", rating, new Date());
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/ratings/user/{userId}")
	public ResponseEntity<ApiResponse<List<Rating>>> getRatingByUserId(@PathVariable String userId) {
		System.out.println("getRatingByUserId cont **");
		List<Rating> userRatings = ratingService.getRatingByUserId(userId);
		ApiResponse<List<Rating>> response = new ApiResponse<>(HttpStatus.OK.value(), true, "Retrieved user's ratings", userRatings, new Date());
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("ratings/hotel/{hotelId}")
	public ResponseEntity<ApiResponse<List<Rating>>> getRatingByHotelId(@PathVariable String hotelId) {
		List<Rating> hotelratings = ratingService.getRatingByHotelId(hotelId);
		ApiResponse<List<Rating>> response = new ApiResponse<>(HttpStatus.OK.value(), true, "Retrieved user's ratings", hotelratings, new Date());
		return ResponseEntity.ok(response);
	}
}
