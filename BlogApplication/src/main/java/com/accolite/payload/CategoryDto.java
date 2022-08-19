package com.accolite.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	private Integer categoryId;
	@NotBlank
	@Size(min = 4)
	private String categoryTitle;
	@NotBlank
	@Size(max = 10)
	private String categoryDescription;

}
