package com.aws.spacecreation.reviewreply;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aws.spacecreation.review.Review;
import com.aws.spacecreation.review.ReviewService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/reply")
@RequiredArgsConstructor
@Controller
public class ReviewReplyController {
	
	private final ReviewService reviewService;
	
	private final ReviewReplyService reviewreplyService;
	
	@PostMapping("/create")
	public String create(Model model, @PathVariable("id") Integer id,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "content") String content)  {
		Review review = this.reviewService.getReview(id);
		reviewreplyService.create(review, username, content);

		return "redirect:/review/${review.id}";
	}

}
