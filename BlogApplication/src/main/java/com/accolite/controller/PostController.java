package com.accolite.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.accolite.config.AppConstants;
import com.accolite.entities.Post;
import com.accolite.payload.ApiResponse;
import com.accolite.payload.FileResponse;
import com.accolite.payload.PostDto;
import com.accolite.payload.PostResponse;
import com.accolite.service.FileService;
import com.accolite.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	@Value("${project.image}")
	private String path;

	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId,@PathVariable Integer categoryId){
		PostDto createPost=this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		List<PostDto> postDtos=this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	@GetMapping("/category/{catId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer catId){
		List<PostDto> postDtos=this.postService.getPostsByCategory(catId);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<PostDto>> getAllPosts(){
		List<PostDto> postdtos=this.postService.getAllPost();
		return new ResponseEntity<List<PostDto>>(postdtos,HttpStatus.OK);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostsById(@PathVariable Integer id){
		PostDto postdtos=this.postService.getPostById(id);
		return new ResponseEntity<PostDto>(postdtos,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer id){
		this.postService.deletePost(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted successfully", true),HttpStatus.OK);
	}
	
	@PutMapping("/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
		PostDto updatePost=this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	
	
	
	//localhost:9090/api/posts/pageable?pageNumber=0&pageSize=2
	
	//will contain pageNumber,pageSize,totalElements,totalPages,lastPage,content
	//customize by implementing class in payload--- PostResponse
	@GetMapping("/pageable")
	public ResponseEntity<List<PostDto>> getAllPostsPageable(@RequestParam(value="pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber, 
			@RequestParam(value="pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize){
		//pageNumber starts from 0
		List<PostDto> postdtos=this.postService.getAllPostsPagination(pageNumber, pageSize);
		return new ResponseEntity<List<PostDto>>(postdtos,HttpStatus.OK);
		
	}
	
	
	@GetMapping("/pageable/customized")
	public ResponseEntity<PostResponse> getAllPostsPageableCustomized(@RequestParam(value="pageNumber", defaultValue = "0",required = false) Integer pageNumber, 
			@RequestParam(value="pageSize", defaultValue = "5",required = false) Integer pageSize){
		//pageNumber starts from 0
		PostResponse allPostsCustomized = this.postService.getAllPostsCustomized(pageNumber, pageSize);
		return new ResponseEntity<PostResponse>(allPostsCustomized,HttpStatus.OK);
		
	}
	
	//localhost:9090/api/posts/pageable?pageNumber=0&pageSize=2&sortBy=title&sortDir=desc
	@GetMapping("/pageable/customized/sorting")
	public ResponseEntity<PostResponse> getAllPostsPageableCustomizedSorting(@RequestParam(value="pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber, 
			@RequestParam(value="pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR,required = false) String sortDir){
		//pageNumber starts from 0
		PostResponse allPostsCustomized = this.postService.getAllPostsCustomized(pageNumber, pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(allPostsCustomized,HttpStatus.OK);
		
	}
	
	
	
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchByTitleContaining(@PathVariable String keyword){
		List<PostDto> postDto=this.postService.searchPost(keyword);
		return new ResponseEntity<List<PostDto>>(postDto,HttpStatus.OK);
	}
	
	
	//"imageName": "IMG_20220628_174356__01.jpg",
	@PostMapping("/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException {
		String fileName = null;
	
			PostDto postDto = this.postService.getPostById(postId); //if not found, returns resourcenotfoundexception
			fileName = this.fileService.uploadImage(path, image); //returns original name. if want random, return fileName1 in impl
			postDto.setImageName(fileName); 
			PostDto updatePost = this.postService.updatePost(postDto, postId);
			return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
		
	}
	
	//in chrome -- http://localhost:9090/api/posts/images/142c8d96-47b8-4deb-b4be-1929abc3c389.jpg
	
	@GetMapping(value = "/images/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName,
			HttpServletResponse response) throws IOException {
		
		InputStream resource=this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
	/*
	 {
    "content": [
        {
            "postId": 1,
            "title": "software language",
            "content": "used to communicate with machine",
            "imageName": "modified",
            "date": "2022-08-14T12:51:43.244+00:00",
            "category": {
                "categoryId": 1,
                "categoryTitle": "engineer",
                "categoryDescription": "software"
            },
            "user": {
                "id": 1,
                "name": "Ananthu",
                "email": "mvananthu@gmail.com",
                "password": "root",
                "about": "IT engineer"
            }
        }
    ],
    "pageNumber": 0,
    "pageSize": 5,
    "totalElements": 1,
    "totalPages": 1,
    "lastPage": true
}
	 */
}
