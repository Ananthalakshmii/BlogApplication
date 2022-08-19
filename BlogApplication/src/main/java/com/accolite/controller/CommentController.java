package com.accolite.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accolite.entities.Comment;
import com.accolite.payload.ApiResponse;
import com.accolite.payload.CommentDto;
import com.accolite.repository.CommentRepo;
import com.accolite.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
	@Autowired
	private CommentService commentService;
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping("/post/{postId}")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable Integer postId){
		CommentDto comment=this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(comment,HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CommentDto> getComment(@PathVariable Integer id){
		Comment com=this.commentRepo.findById(id).get();
		CommentDto comDto=this.modelMapper.map(com, CommentDto.class);
		return new ResponseEntity<CommentDto>(comDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully", true),HttpStatus.OK);
	}

}
