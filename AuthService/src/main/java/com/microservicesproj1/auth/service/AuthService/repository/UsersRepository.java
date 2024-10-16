package com.microservicesproj1.auth.service.AuthService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicesproj1.auth.service.AuthService.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer>{
	Users findByUsername(String username);
}
