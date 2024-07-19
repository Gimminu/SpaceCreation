package com.aws.spacecreation.answer;

import com.aws.spacecreation.review.Information;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aws.spacecreation.review.InforService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/answer")
public class AnswerController {

	private final InforService inforService;
	private final AnswerService answerService;
	
	@PostMapping("/create/{id}")
	public String createAnswer(Model model,
						@PathVariable("id")Integer id,
						@RequestParam(value="content")String content) {
		Information information = this.inforService.getQuestion(id);
		
		answerService.create(information, content);
		return "redirect:/infomation/detail/"+id;
	}
		
}
