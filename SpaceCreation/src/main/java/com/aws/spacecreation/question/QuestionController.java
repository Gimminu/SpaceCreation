package com.aws.spacecreation.question;


import com.aws.spacecreation.user.UserSecuritySerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/question")
public class QuestionController {
	private final QuestionRepository questionRepository;
	private final QuestionService questionService;
    private final UserSecuritySerivce userSecuritySerivce;
	
	@Value("${cloud.aws.s3.endpoint}")
	private String downpath;
	
    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(defaultValue = "0", name = "page") int page,
                       @RequestParam(defaultValue = "views", name = "sort") String sort,
                       @RequestParam(defaultValue = "desc", name = "direction") String direction) {
        int pageSize = 10;
        Sort sortOrder = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, pageSize, sortOrder);
        Page<Question> questionPage = questionService.getAllQuestions(pageable);
        model.addAttribute("questionPage", questionPage);
        model.addAttribute("sort", sort); // 추가: 정렬 기준
        model.addAttribute("direction", direction); // 추가: 정렬 방향
        return "view/info/question_list";
    }
    
    //@GetMapping(value = "/question/detail/{id}")
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "view/info/question_detail";
    }
    
    @GetMapping("/create")
    public String questionCreate(Model model) {
        model.addAttribute("question", new Question());
        return "view/info/question_form";
    }
    
    @PostMapping("/create")
    public String questionCreate(@ModelAttribute Question question) {
        questionService.create(question);
        return "redirect:/question/list";
    }
    
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        questionService.delete(id);
        return "redirect:/question/list";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id")Integer id, Model model){
        Question question = questionService.getQuestion(id);
        if(userSecuritySerivce.getauthen().equals(question.getUser())) {
            model.addAttribute("question", question);
            return "view/info/question_update";
        }else{
            throw new SecurityException("게시물의 작성자만 수정할 수 있습니다.");
        }
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id")Integer id, Question question){
        questionService.update(id, question);
        return "redirect:/question/list";
    }

    
}