package com.aws.spacecreation.answer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aws.spacecreation.review.Infomation;
import com.aws.spacecreation.review.InfoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/answer")
public class AnswerController {

	private final InfoService infoService;
	private final AnswerService answerService;
	
	@PostMapping("/create/{id}")
	public String createAnswer(Model model,
						@PathVariable("id")Integer id,
						@RequestParam(value="content")String content) {
		Infomation infomation = this.infoService.getQuestion(id);
		
		answerService.create(infomation, content);
		return "redirect:/infomation/detail/"+id;
	}
		
}
