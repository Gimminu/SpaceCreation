package com.aws.spacecreation;

import com.aws.spacecreation.answer.Answer;
import com.aws.spacecreation.interiorboard.InteriorBoardService;
import com.aws.spacecreation.question.Question;
import com.aws.spacecreation.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final InteriorBoardService interiorBoardservice;
    private final QuestionRepository questionRepository;
    @GetMapping("/")
    public String root(Model model)
    {
        model.addAttribute("InteriorBoard", interiorBoardservice.getAllInteriorBoards());
        return "view/index";
    }
    @GetMapping("/index")
    public String index() {return "redirect:/";}
    @GetMapping("/home")
    public String home() {
        return "view/index";
    }
    @GetMapping("/about")
    public String about() {
        return "view/about";
    }

    @GetMapping("/faq")
    public String fag(Model model){
        List<Question> topQuestions = questionRepository.findTop5ByOrderByViewsDesc();
        model.addAttribute("topQuestions", topQuestions);

        return "view/faq";
    }
}
