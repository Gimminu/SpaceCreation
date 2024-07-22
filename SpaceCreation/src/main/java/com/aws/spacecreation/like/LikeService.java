package com.aws.spacecreation.like;

import com.aws.spacecreation.user.UserSecuritySerivce;
import org.springframework.stereotype.Service;

import com.aws.spacecreation.question.Review;
import com.aws.spacecreation.question.ReviewRepository;
import com.aws.spacecreation.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikeService {
	
	private final LikeRepository likeRepository;
	private final UserSecuritySerivce userSecuritySerivce;
	private final ReviewRepository reviewRepository;
	
	public void like(Review review) {
		SiteUser username = userSecuritySerivce.getauthen();
		Likes like = new Likes();
		like.setUsername(username.getUsername());
		like.setReview(review);
		likeRepository.save(like);
		review.setLikes(review.getLikes()+1);
		this.reviewRepository.save(review);
	}
	
	public void delete(Review review) {
		
		SiteUser username = userSecuritySerivce.getauthen();
		likeRepository.findByReviewAndUsername(review, username.getUsername());
		review.setLikes(review.getLikes()-1);
		this.reviewRepository.save(review);
	}
}
