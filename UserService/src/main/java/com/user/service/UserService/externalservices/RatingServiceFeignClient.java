package com.user.service.UserService.externalservices;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "RATINGSERVICE")
public interface RatingServiceFeignClient {
	
}
