package com.microservice.rating.RatingService.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.rating.RatingService.entities.Rating;
import com.microservice.rating.RatingService.services.RatingService;

@RestController
public class RatingController {
	
	@Autowired
	RatingService ratingService;
	
	@PostMapping("/ratings")
	public ResponseEntity<Rating> create(@RequestBody Rating rating) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.create(rating));
	}
	
	@GetMapping("/ratings")
	public ResponseEntity<List<Rating>> getAllRating() {
		return ResponseEntity.ok(ratingService.getAllRatings());
	}
	
	@GetMapping("/ratings/{ratingId}")
	public ResponseEntity<Rating> getRating(@PathVariable String ratingId) {
		return ResponseEntity.ok(ratingService.getRating(ratingId));
	}
	
	@GetMapping("/ratings/user/{userId}")
	public ResponseEntity<List<Rating>> getRatingByUserId(@PathVariable String userId) {
		System.out.println("getRatingByUserId cont **");
		return ResponseEntity.ok(ratingService.getRatingByUserId(userId));
	}
	
	@GetMapping("ratings/hotel/{hotelId}")
	public ResponseEntity<List<Rating>> getRatingByHotelId(@PathVariable String hotelId) {
		return ResponseEntity.ok(ratingService.getRatingByHotelId(hotelId));
	}
}
