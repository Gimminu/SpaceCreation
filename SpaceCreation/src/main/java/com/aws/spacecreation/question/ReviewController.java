package com.aws.spacecreation.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
			@RequestParam("file") MultipartFile file) throws IOException {
		reviewService.create(review,file);
		return"redirect:/review";
	}
	
	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Review review = this.reviewService.getReview(id);
		model.addAttribute("review", review);
		model.addAttribute("downpath", "https://" + downpath);
		return "review_detail";
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
