package com.aws.spacecreation.review;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/review")
@Controller
public class ReviewController {
	
	@Value("${cloud.aws.s3.endpoint}")
	private String downpath;
	
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
	
	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Review review = this.reviewService.getReview(id);
		model.addAttribute("review", review);
		model.addAttribute("downpath", "https://" + downpath);
		return "review_create";
	}
	
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		reviewService.delete(id);
		return "redirect:/review/list";
	}
	
	@GetMapping("/searchkw")
	public String searchkw(Model model, @RequestParam("kw")String kw) {
		model.addAttribute("reviewList", reviewService.searchkw(kw));
		return "review";
	}
	
	
	
	
	
	
	
	
}
