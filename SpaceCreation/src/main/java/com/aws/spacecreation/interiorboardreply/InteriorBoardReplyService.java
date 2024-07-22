package com.aws.spacecreation.interiorboardreply;

import java.time.LocalDateTime;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.aws.spacecreation.interiorboard.InteriorBoard;
import com.aws.spacecreation.user.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InteriorBoardReplyService {
	private JavaMailSender mailSender;
	private final InteriorBoardReplyRepository interiorBoardReplyRepository;
	private final UserService userService;
	
	public void create(InteriorBoard interiorBoard,  String content) {
		
		InteriorBoardReply interiorBoardReply = new InteriorBoardReply();
		
//		interiorBoardReply.setUsername(username);
		interiorBoardReply.setContent(content);
		interiorBoardReply.setCreateDate(LocalDateTime.now());
		interiorBoardReply.setInteriorBoard(interiorBoard);
		this.interiorBoardReplyRepository.save(interiorBoardReply);
		
//		SiteUser postername = userService.authen();

		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("yoon61765@naver.com");
		message.setTo(interiorBoard.getPoster());
		message.setSubject("다음의 글에 댓글이 작성되었습니다. ["+ interiorBoard.getSubject() +"]");
		message.setText(content);

		mailSender.send(message);
	}
}
