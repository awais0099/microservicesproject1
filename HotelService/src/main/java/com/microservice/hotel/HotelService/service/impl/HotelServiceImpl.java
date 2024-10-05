package com.microservice.hotel.HotelService.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.hotel.HotelService.entities.Hotel;
import com.microservice.hotel.HotelService.exceptions.ResourceNotFoundException;
import com.microservice.hotel.HotelService.repositories.HotelRepository;
import com.microservice.hotel.HotelService.service.HotelService;

@Service
public class HotelServiceImpl implements HotelService {
	
	@Autowired
	HotelRepository hotelRepository;
	
	@Override
	public Hotel create(Hotel hotel) {
		String random_id = UUID.randomUUID().toString();
		hotel.setId(random_id);
		return hotelRepository.save(hotel);
	}

	@Override
	public List<Hotel> getAllHotel() {
		List<Hotel> hotels = hotelRepository.findAll();
		return hotels;
	}

	@Override
	public Hotel getHotel(String id) {
		return hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel resource has not found with the given id: " + id));
	}
}
