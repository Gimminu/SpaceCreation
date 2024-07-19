package com.aws.spacecreation.answer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
	    answerService.delete(id);
	    return "redirect:/question/detail/{id}";
	}
	
	@PostMapping("/delete/{id}")
    public String deleteAnswer(@PathVariable("id")Integer id) {
		Answer answer = this.answerService.getAnswer(id);
    	answerService.delete(id);
    	return String.format("redirect:/question/detail/%s",answer.getQuestion().getId());
    }
		
}
