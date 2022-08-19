package com.accolite.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.accolite.payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	//instead of getting 500 - internal server error when id is not found- displaying it as a message
	//1. @RestControllerAdvice
	//2. @ExceptionHandler on a method along with the exception class which it handles
	
	@ExceptionHandler(ResourceNotFoundException.class) //when id is not present, when jwt username does not meet
	public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException ex){ 
		String message=ex.getMessage();
		ApiResponse apiResponse=new ApiResponse(message, false);
		return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class) //when validation does not meet
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		Map<String, String> response=new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach(error->{
			String fieldName=((FieldError)error).getField();
			String message=error.getDefaultMessage();
			response.put(fieldName, message);
		});
		
		return new ResponseEntity<Map<String,String>>(response,HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(ApiException.class) //when jwt password doesnt meet
	public ResponseEntity<ApiResponse> handleApiException(ApiException ex){ 
		String message=ex.getMessage();
		ApiResponse apiResponse=new ApiResponse(message, true);
		return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
	}
	

}
