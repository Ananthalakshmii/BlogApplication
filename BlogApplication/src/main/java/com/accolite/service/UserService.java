package com.accolite.service;

import java.util.List;

import com.accolite.entities.User;
import com.accolite.payload.UserDto;


public interface UserService {
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Integer id);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);
	
	UserDto registerNewUser(UserDto userDto);
}
