package com.aws.spacecreation;

import com.aws.spacecreation.interiorboard.InteriorBoardService;
import com.aws.spacecreation.question.Question;
import com.aws.spacecreation.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final InteriorBoardService interiorBoardservice;
    private final QuestionRepository questionRepository;
    @GetMapping("/")
    public String root(Model model)
    {
        model.addAttribute("InteriorBoard", interiorBoardservice.getAllInteriorBoards());
        return "View/index";
    }
    @GetMapping("/index")
    public String index() {return "redirect:/";}
    @GetMapping("/home")
    public String home() {
        return "View/index";
    }
    @GetMapping("/about")
    public String about() {
        return "View/about";
    }

    @GetMapping("/faq")
    public String fag(Model model){
        List<Question> topQuestions = questionRepository.findTop5ByOrderByViewsDesc();
        model.addAttribute("topQuestions", topQuestions);

        return "View/faq";
    }
}
