package com.aws.spacecreation.review;


import java.io.IOException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/question")
@Controller
public class QuestionController {
	private final QuestionRepository questionRepository;
	private final QuestionService questionService;
	
	@Value("${cloud.aws.s3.endpoint}")
	private String downpath;
	
    @GetMapping("/list")
    public String list(Model model) {
        List<Question> questionList = this.questionRepository.findAll();
        model.addAttribute("questionList", questionList);
        return "view/question/question_list";
    }
    
    //@GetMapping(value = "/question/detail/{id}")
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
    	
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        model.addAttribute("downpath", "https://" + downpath);

        return "view/question/question_detail";
    }
    
    @GetMapping("/create")
    public String questionCreate() {
        return "view/question/question_form";
    }
    
    @PostMapping("/create")
    public String questionCreate(@ModelAttribute Question question,
    		@RequestParam ("files") MultipartFile[] files) throws IOException {
        // TODO 질문을 저장한다.
    		questionService.create(question, files);
        return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
    }
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
    	
        questionService.delete(id);

        return "redirect:/question/list"; //  질문목록으로 이동
    }
    
}