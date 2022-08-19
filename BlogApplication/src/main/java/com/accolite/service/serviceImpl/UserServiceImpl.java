package com.accolite.service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.accolite.config.AppConstants;
import com.accolite.entities.Role;
import com.accolite.entities.User;
import com.accolite.payload.UserDto;
import com.accolite.repository.RoleRepo;
import com.accolite.repository.UserRepo;
import com.accolite.service.UserService;
import com.accolite.exception.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper; // create a bean in main class and autowire
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	/*private User dtoToUser(UserDto userDto) {
		User user=new User();
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		System.out.println("userDto object "+userDto.getName()+" "+userDto.getEmail()+" "+userDto.getPassword()+" "+userDto.getAbout());
		System.out.println("inside dtoUser "+user.getName()+" "+user.getEmail()+" "+user.getPassword()+" "+user.getAbout());
		return user;
	}
	
	private UserDto userToDto(User user) {
		UserDto userDto=new UserDto();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setAbout(user.getAbout());
		return userDto;
	}*/

	private User dtoToUser(UserDto userDto) {
		return modelMapper.map(userDto, User.class);
	}
	
	private UserDto userToDto(User user) {
		return modelMapper.map(user, UserDto.class);
	}
	
	
	@Override
	public UserDto createUser(UserDto userDto) {
		System.out.println("inside create user "+userDto);
		User user=this.dtoToUser(userDto);
		User savedUser=userRepo.save(user);
		return userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer id) {
		User user=userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","Id",id));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser=userRepo.save(user);
		UserDto userDto1=userToDto(updatedUser);
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		return userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users=userRepo.findAll();
		List<UserDto> userdDtos=users.stream().map(user->userToDto(user)).collect(Collectors.toList());
		return userdDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		userRepo.delete(user);
		
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto, User.class);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User savedUser = this.userRepo.save(user);
		return this.modelMapper.map(savedUser, UserDto.class);
	}

}
