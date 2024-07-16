package com.aws.spacecreation.answer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aws.spacecreation.review.Question;
import com.aws.spacecreation.review.QuestionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/answer")
public class AnswerController {

	private final QuestionService questionService;
	private final AnswerService answerService;
	
	@PostMapping("/create/{id}")
	public String createAnswer(Model model,
						@PathVariable("id")Integer id,
						@RequestParam(value="content")String content) {
		Question question = this.questionService.getQuestion(id);
		
		answerService.create(question, content);
		return "redirect:/question/detail/"+id;
	}
		
}