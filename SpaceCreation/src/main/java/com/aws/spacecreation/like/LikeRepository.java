package com.aws.spacecreation.like;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aws.spacecreation.question.Review;

public interface LikeRepository extends JpaRepository<Likes, Integer> {
	Optional<Likes> findByReviewAndUsername(Review review, String username);
}
