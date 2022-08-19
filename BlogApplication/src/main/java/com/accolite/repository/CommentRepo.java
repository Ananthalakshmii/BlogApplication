package com.accolite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accolite.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
