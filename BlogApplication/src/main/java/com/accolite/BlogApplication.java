package com.accolite;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.accolite.config.AppConstants;
import com.accolite.entities.Role;
import com.accolite.repository.RoleRepo;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner{
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(passwordEncoder.encode("vruthu1")); // use distinct mail id to retrieve single value 
		//and use eager type to initialise proxy and initiate session
		//so using vruthu@gmail.com and vruthu1..
		//copy password that is printed in console and change it in db in mySQL workbench and apply the changes 
		// and check in postman with basic authentication
		
		try {
			Role role=new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");
			
			Role role1=new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_NORMAL");
			
			List<Role> roles = List.of(role,role1);
			this.roleRepo.saveAll(roles);
			
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}

}
