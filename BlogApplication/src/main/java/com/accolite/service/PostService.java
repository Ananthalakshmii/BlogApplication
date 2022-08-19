package com.accolite.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.accolite.entities.Category;
import com.accolite.entities.Post;
import com.accolite.payload.PostDto;
import com.accolite.payload.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	PostDto updatePost(PostDto postDto,Integer postId);
	void deletePost(Integer postId);
	List<PostDto> getAllPost();
	PostDto getPostById(Integer id);
	List<PostDto> getPostsByCategory(Integer categoryId);
	List<PostDto> getPostByUser(Integer userId);
	
	@Query("select p from Post p where p.title like:keyword") //not necessary - to overcome InvalidDataAccessApiUsageException from hibernate jar-previous version
	List<PostDto> searchPost(@Param("keyword") String keyword); //@param also not necessary
	
	List<PostDto> getAllPostsPagination(Integer pageNumber,Integer pageSize);
	PostResponse getAllPostsCustomized(Integer pageNumber,Integer pageSize);
	PostResponse getAllPostsCustomized(Integer pageNumber, Integer pageSize, String sortBy,String sortDir);

}
