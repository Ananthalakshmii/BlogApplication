package com.accolite.payload;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.accolite.entities.Category;
import com.accolite.entities.Comment;
import com.accolite.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	
	private Integer postId;
	private String title;
	private String content;
	private String imageName;
	private Date date;
	private CategoryDto category; //to avoid infinite loop-- post-category-post..changing to dto
	private UserDto user;
	
	private Set<CommentDto> comments=new HashSet<>();

}
