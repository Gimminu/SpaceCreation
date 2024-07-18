package com.aws.spacecreation.like;

import org.springframework.stereotype.Service;

import com.aws.spacecreation.review.Review;
import com.aws.spacecreation.review.ReviewRepository;
import com.aws.spacecreation.user.SiteUser;
import com.aws.spacecreation.user.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikeService {
	
	private final LikeRepository likeRepository;
	private final UserService userService;
	private final ReviewRepository reviewRepository;
	
	public void like(Review review) {
		SiteUser username = userService.authen();
		
		Likes like = new Likes();
		like.setUsername(username.getUsername());
		like.setReview(review);
		likeRepository.save(like);
		review.setLikes(review.getLikes()+1);
		this.reviewRepository.save(review);
	}
	
	public void delete(Review review) {
		
		SiteUser username = userService.authen();
		likeRepository.findByReviewAndUsername(review, username.getUsername());
		review.setLikes(review.getLikes()-1);
		this.reviewRepository.save(review);
	}
}
