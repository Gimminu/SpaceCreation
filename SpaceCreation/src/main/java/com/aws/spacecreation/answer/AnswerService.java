package com.aws.spacecreation.answer;

import java.time.LocalDateTime;

import com.aws.spacecreation.review.Information;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service

public class AnswerService {

	private final AnswerRepository answerRepository;
	
	public void create(Information information, String content) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setInformation(information);
		this.answerRepository.save(answer);
		
	}
	
	
}
