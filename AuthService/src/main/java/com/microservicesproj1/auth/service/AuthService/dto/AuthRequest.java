package com.microservicesproj1.auth.service.AuthService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthRequest {
	private String username;
	private String password;
}
