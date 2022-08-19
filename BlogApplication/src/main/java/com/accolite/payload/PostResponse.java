package com.accolite.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse { // for pageable class helper
	
	private List<PostDto> content;
	private int pageNumber;
	private int pageSize;
	private Long totalElements;
	private int totalPages;
	private boolean lastPage;

}
