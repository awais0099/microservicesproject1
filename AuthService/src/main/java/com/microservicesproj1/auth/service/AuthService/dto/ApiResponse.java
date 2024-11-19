package com.microservicesproj1.auth.service.AuthService.dto;

import java.time.Instant;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApiResponse<T> {
	private int status;
	private boolean success;
	private String message;
	private String timestamp;
	private T data;
	
	public ApiResponse(int status, boolean success, String message, T data, Date date) {
		this.status = status;
		this.success = success;
		this.message = message;
		this.data = data;
		this.timestamp = Instant.now().toString();
	}
}
