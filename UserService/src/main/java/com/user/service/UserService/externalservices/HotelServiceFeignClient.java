package com.user.service.UserService.externalservices;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.user.service.UserService.entities.Hotel;

@FeignClient(name = "HOTELSERVICE")
public interface HotelServiceFeignClient {
	@GetMapping("/hotels/{hotelId}")
	Hotel getHotel(@PathVariable("hotelId") String hotelId);
}
