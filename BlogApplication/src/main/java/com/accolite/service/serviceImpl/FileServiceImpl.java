package com.accolite.service.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.accolite.service.FileService;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		//file name
		String name=file.getOriginalFilename();//abc.png
		
		//generate random name for file
		String randomID=UUID.randomUUID().toString();
		String fileName1=randomID.concat(name.substring(name.lastIndexOf("."))); //eg: 142c8d96-47b8-4deb-b4be-1929abc3c389.png
		
		//full path
		String filePath=path+File.separator+fileName1;
		
		//create folder if not created
		File file1=new File(path);
		if(!file1.exists())
			file1.mkdir();
		
		//file copy
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return name;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {

		String fullPath=path+File.separator+fileName;
		InputStream is=new FileInputStream(fullPath);
		return is;
	}

}
