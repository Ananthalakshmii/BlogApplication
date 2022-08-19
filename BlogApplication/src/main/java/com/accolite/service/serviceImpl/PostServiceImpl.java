package com.accolite.service.serviceImpl;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PostRemove;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.accolite.entities.Category;
import com.accolite.entities.Post;
import com.accolite.entities.User;
import com.accolite.exception.ResourceNotFoundException;
import com.accolite.payload.PostDto;
import com.accolite.payload.PostResponse;
import com.accolite.repository.CategoryRepo;
import com.accolite.repository.PostRepo;
import com.accolite.repository.UserRepo;
import com.accolite.service.PostService;

@Service
public class PostServiceImpl implements PostService{
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "user id", userId));
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Catgory ", "category id", categoryId));
		
		Post post=this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddDate(new Date());
		post.setUser(user);
		post.setCategory(cat);
		
		Post newpost=this.postRepo.save(post);
		
		return this.modelMapper.map(newpost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post ", "post id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost= this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post ", "post id", postId));
		this.postRepo.delete(post);
	}

	@Override
	public List<PostDto> getAllPost() {
		List<Post> posts=this.postRepo.findAll();
		List<PostDto> postDto=posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDto;
	}

	@Override
	public PostDto getPostById(Integer id) {
		Post posts=this.postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Post ", "post id", id));
		PostDto postDto=this.modelMapper.map(posts, PostDto.class);
		return postDto;
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category ", "category id", categoryId));
		List<Post> posts=this.postRepo.findByCategory(cat);
		List<PostDto> postDtos= posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User ", "user id", userId));
		List<Post> posts=this.postRepo.findByUser(user);
		List<PostDto> postDto=posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDto;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> posts= this.postRepo.findByTitleContaining(keyword); //("%"+keyword+"%") to overcome hibernate issue in 5.6.7
		List<PostDto> postDtos=posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getAllPostsPagination(Integer pageNumber, Integer pageSize) {
		PageRequest page=PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePost=this.postRepo.findAll(page);
		List<Post> posts=pagePost.getContent();
		List<PostDto> postDto=posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDto;
	}

	@Override
	public PostResponse getAllPostsCustomized(Integer pageNumber, Integer pageSize) {
		PageRequest page=PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePost=this.postRepo.findAll(page);
		List<Post> posts=pagePost.getContent();
		List<PostDto> postDto=posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDto);
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		
		return postResponse;
	}

	@Override
	public PostResponse getAllPostsCustomized(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc"))
			sort=Sort.by(sortBy).ascending();
		else
			sort=Sort.by(sortBy).descending();
		
		PageRequest page=PageRequest.of(pageNumber, pageSize,sort); //adding sort object in ascending or descending
		Page<Post> pagePost=this.postRepo.findAll(page);
		List<Post> posts=pagePost.getContent();
		List<PostDto> postDto=posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDto);
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		
		return postResponse;
	}

}
