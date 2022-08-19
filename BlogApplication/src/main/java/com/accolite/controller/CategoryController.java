package com.accolite.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accolite.payload.ApiResponse;
import com.accolite.payload.CategoryDto;
import com.accolite.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto categoryDto1=this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(categoryDto1,HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> updateCategoryDto(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer id){
		CategoryDto categoryDto2=this.categoryService.updateCategory(categoryDto, id);
		return new ResponseEntity<CategoryDto>(categoryDto2,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteCategoryDto(@PathVariable Integer id){
		this.categoryService.deleteCategory(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("category deleted successfully",true),HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getCategoryDto(@PathVariable Integer id){
		CategoryDto categoryDto=this.categoryService.getCategory(id);
		return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategoryDto(){
		List<CategoryDto> categoryDtos=this.categoryService.getAllCategories();
		return new ResponseEntity<List<CategoryDto>>(categoryDtos,HttpStatus.OK);
	}
}
