package com.accolite.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.accolite.payload.FileResponse;
import com.accolite.service.FileService;

@RestController
@RequestMapping("/file")
public class FileController { //for project related implementation, check PostController
	
	@Autowired
	private FileService fileService;
	@Value("${project.image}")
	private String path;
	
	
	//in postman, type link.. -> body-> key as "image" and change it to "file" type and browse the image
	//from postman working directory- C:\Users\anantha.mv\Postman\files(can be changed in postman settings->working directory
	
	/*
	 {
    "fileName": "IMG_20220628_174356__01.jpg",
    "message": "image is uploaded successfully"
	}
	*/
	
	//file will be saved in project.. BlogApplication/images/uploadedImage  -- taken from application.properties
	
	@PostMapping("/upload")
	public ResponseEntity<FileResponse> fileUpload(@RequestParam("image") MultipartFile image) {
		String fileName = null;
		try {
			fileName = this.fileService.uploadImage(path, image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(new FileResponse(null,"image is not uploaded due to error on server"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(new FileResponse(fileName,"image is uploaded successfully"),HttpStatus.OK);
	}
	
	
	//method to server files
	
	//in chrome -- http://localhost:9090/file/images/142c8d96-47b8-4deb-b4be-1929abc3c389.jpg
	@GetMapping(value = "/images/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName,
			HttpServletResponse response) throws IOException {
		
		InputStream resource=this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}

}
