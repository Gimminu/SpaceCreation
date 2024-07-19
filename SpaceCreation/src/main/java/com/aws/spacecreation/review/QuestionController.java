package com.aws.spacecreation.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

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
        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @GetMapping("/create")
    public String questionCreate(Model model) {
        model.addAttribute("question", new Question());
        return "question_form";
    }

    @PostMapping("/create")
    public String questionCreate(@ModelAttribute Question question) {
        questionService.create(question);
        return "redirect:/question/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        questionService.delete(id);
        return "redirect:/question/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable("id") Integer id) {
        questionService.deleteQuestion(id);
        return "redirect:/question/list";
    }
}
