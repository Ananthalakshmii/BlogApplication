package com.accolite.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accolite.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	
	Optional<User> findByEmail(String email);

}
