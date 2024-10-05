package com.microservice.hotel.HotelService.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.hotel.HotelService.entities.Hotel;
import com.microservice.hotel.HotelService.service.HotelService;

@RestController
public class HotelController {
	
	@Autowired
	private HotelService hotelService;
	
	@PostMapping("/hotels")
	public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
		Hotel newHotel = hotelService.create(hotel);
		return ResponseEntity.status(HttpStatus.CREATED).body(newHotel);
	}
	
	@GetMapping("/hotels")
	public ResponseEntity<List<Hotel>> getAllHotels() {
		List<Hotel> hotels = hotelService.getAllHotel();
		return ResponseEntity.ok(hotels);
	}
	
	@GetMapping("/hotels/{hotelId}")
	public ResponseEntity<Hotel> getHotel(@PathVariable String hotelId) {
		Hotel hotel = hotelService.getHotel(hotelId);
		return ResponseEntity.status(HttpStatus.OK).body(hotel);
	}
}
