package com.aws.spacecreation.review;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/review")
@Controller
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;

	@GetMapping("")
	public String review() {
		return "review_list";
	}

	@GetMapping("/create")
	public String create() {
		return "review_create";
	}
	
	@PostMapping("/create")
	public String create(@ModelAttribute Review review,
			@RequestParam("file1") MultipartFile file1,
			@RequestParam("file2") MultipartFile file2,
			@RequestParam("file3") MultipartFile file3) throws IOException {
		reviewService.create(review,file1,file2,file3);
		return"redirect:/review";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
