package com.accolite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accolite.entities.Category;
import com.accolite.entities.Post;
import com.accolite.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String title);

}
