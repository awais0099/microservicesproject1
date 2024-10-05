package com.microservice.hotel.HotelService.service;

import java.util.List;

import com.microservice.hotel.HotelService.entities.Hotel;

public interface HotelService {
	Hotel create(Hotel hotel);
	List<Hotel> getAllHotel();
	Hotel getHotel(String id);
}
