package com.aws.spacecreation.answer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.aws.spacecreation.review.Infomation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service

public class AnswerService {

	private final AnswerRepository answerRepository;
	
	public void create(Infomation infomation, String content) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setInfomation(infomation);
		this.answerRepository.save(answer);
		
	}
	
	
}
