package com.accolite.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.accolite.entities.User;
import com.accolite.exception.ResourceNotFoundException;
import com.accolite.repository.UserRepo;

@Service
public class CustomerUserDetailsService implements UserDetailsService{ //to fetch user data from database
	@Autowired
	private UserRepo userRepo;

	
	//since this need to return UserDetails and we get User -> we implement Userdetails in user class
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
		// loading user from database by username
		
		User user=this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("user ", "email id"+username, 0));
		return user;
	}

}
