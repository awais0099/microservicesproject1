package com.microservice.hotel.HotelService.controllers;

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

import com.microservice.hotel.HotelService.dto.ApiResponse;
import com.microservice.hotel.HotelService.entities.Hotel;
import com.microservice.hotel.HotelService.service.HotelService;

@RestController
public class HotelController {
	
	@Autowired
	private HotelService hotelService;
	
	@PostMapping("/hotels")
	public ResponseEntity<ApiResponse<Hotel>> createHotel(@RequestBody Hotel hotel) {
		Hotel newHotel = hotelService.create(hotel);
		ApiResponse<Hotel> response = new ApiResponse<>(HttpStatus.OK.value(), true, "Created hotel", newHotel, new Date());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/hotels")
	public ResponseEntity<ApiResponse<List<Hotel>>> getAllHotels() {
		List<Hotel> hotels = hotelService.getAllHotel();
		ApiResponse<List<Hotel>> response = new ApiResponse<>(HttpStatus.OK.value(), true, "Retreived hotels", hotels, new Date());
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/hotels/{hotelId}")
	public ResponseEntity<ApiResponse<Hotel>> getHotel(@PathVariable String hotelId) {
		Hotel hotel = hotelService.getHotel(hotelId);
		ApiResponse<Hotel> response = new ApiResponse<>(HttpStatus.OK.value(), true, "Created hotel", hotel, new Date());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
