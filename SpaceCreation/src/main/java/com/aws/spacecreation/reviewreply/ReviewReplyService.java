package com.aws.spacecreation.reviewreply;

import java.time.LocalDateTime;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.aws.spacecreation.review.Review;
import com.aws.spacecreation.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewReplyService {
	
	private final ReviewReplyRepository reviewReplyRepository;
	
	public void create(Review review, String username, String content) {
		
		ReviewReply reviewReply = new ReviewReply();
		
		reviewReply.setUsername(username);
		reviewReply.setContent(content);
		reviewReply.setCreateDate(LocalDateTime.now());
		reviewReply.setReview(review);
		this.reviewReplyRepository.save(reviewReply);
		
		SiteUser username = userService.authen();

		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("yoon61765@naver.com");
		message.setTo(review.getPoster());
		message.setSubject("다음의 글에 댓글이 작성되었습니다. ["+ review.getSubject() +"]");
		message.setText(content);

		mailSender.send(message);
	}
}
