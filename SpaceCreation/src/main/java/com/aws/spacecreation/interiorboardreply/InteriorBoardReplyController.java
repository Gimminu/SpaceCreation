package com.aws.spacecreation.reviewreply;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aws.spacecreation.question.Review;
import com.aws.spacecreation.question.ReviewService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/reply")
@RequiredArgsConstructor
@Controller
public class ReviewReplyController {
	
	private final ReviewService reviewService;
	
	private final ReviewReplyService reviewreplyService;
	
	@PostMapping("/create/{id}")
	public String create(Model model, @PathVariable("id") Integer id,
//			@RequestParam(value = "username") String username,
			@RequestParam(value = "content") String content)  {
		Review review = this.reviewService.getReview(id);
		reviewreplyService.create(review,  content);

		return String.format("redirect:/review/detail/%s", id);
	}

}
