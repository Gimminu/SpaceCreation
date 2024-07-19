package com.aws.spacecreation.like;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.aws.spacecreation.review.Review;
import com.aws.spacecreation.review.ReviewService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class LikeController {
	
	private final LikeService likeService;
	private final ReviewService reviewService;
	
	@PostMapping("/like/{id}")
	public void like(Model model, @PathVariable("id") Integer id) {
		Review review = this.reviewService.getReview(id);
		likeService.like(review);
	}
	
	@PostMapping("/like/delete/{id}")
	public void like_delete(Model model, @PathVariable("id") Integer id) {
		Review review = this.reviewService.getReview(id);
		likeService.delete(review);
	}
}
