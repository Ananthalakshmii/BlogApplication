package com.accolite.service;

import com.accolite.payload.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto, Integer postId);
	void deleteComment(Integer commentId);

}
