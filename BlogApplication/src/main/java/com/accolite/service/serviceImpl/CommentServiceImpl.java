package com.accolite.service.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.accolite.entities.Comment;
import com.accolite.entities.Post;
import com.accolite.exception.ResourceNotFoundException;
import com.accolite.payload.CommentDto;
import com.accolite.payload.PostDto;
import com.accolite.repository.CommentRepo;
import com.accolite.repository.PostRepo;
import com.accolite.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;

	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "post id", postId));
		
		Comment comment=this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment=this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment com=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment", "comment id", commentId));
		System.out.println(com.getId()+" "+com.getContent());
		this.commentRepo.deleteById(commentId);
		
	}

}
