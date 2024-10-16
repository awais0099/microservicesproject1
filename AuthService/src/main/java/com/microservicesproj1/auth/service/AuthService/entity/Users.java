package com.microservicesproj1.auth.service.AuthService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
}
