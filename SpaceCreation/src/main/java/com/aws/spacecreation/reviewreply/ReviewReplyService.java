package com.aws.spacecreation.reviewreply;

import com.aws.spacecreation.question.Review;
import com.aws.spacecreation.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ReviewReplyService {
	private JavaMailSender mailSender;
	private final ReviewReplyRepository reviewReplyRepository;
	private final UserService userService;
	
	public void create(Review review,  String content) {
		
		ReviewReply reviewReply = new ReviewReply();
		
//		reviewReply.setUsername(username);
		reviewReply.setContent(content);
		reviewReply.setCreateDate(LocalDateTime.now());
		reviewReply.setReview(review);
		this.reviewReplyRepository.save(reviewReply);
		
//		SiteUser postername = userService.authen();

		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("yoon61765@naver.com");
		message.setTo(review.getPoster());
		message.setSubject("다음의 글에 댓글이 작성되었습니다. ["+ review.getSubject() +"]");
		message.setText(content);

		mailSender.send(message);
	}
}
