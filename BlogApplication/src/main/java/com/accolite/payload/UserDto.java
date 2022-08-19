package com.accolite.payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto { //directly exposing to the 3rd party, entity can have dynamically added attributes
	//data transfer object
	
	private int id;
	
	@NotEmpty //null or empty
	@Size(min = 4,message = "Username must be min of 4 characters") //if any of the validation fails, it throws MethodArgumentNotValidException (in postman)
	private String name;
	
	@Email(message = "email address is not valid")
	private String email;
	
	@NotEmpty
	@Size(min=3,max=10,message = "password must be min of 3 chars and max of 10 chars")
	//@Pattern(regexp = )
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<RoleDto> roles=new HashSet<>();

}
