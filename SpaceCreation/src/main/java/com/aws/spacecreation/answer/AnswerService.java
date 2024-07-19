package com.aws.spacecreation.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.aws.spacecreation.review.Question;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service

public class AnswerService {

	private final AnswerRepository answerRepository;
	
	public void create(Question question, String content) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question);
		this.answerRepository.save(answer);
		
	}
	public Answer getAnswer(Integer id) {
		return answerRepository.getReferenceById(id);
	}
	public void delete(Integer id) {
		answerRepository.deleteById(id);
	}
	
}
