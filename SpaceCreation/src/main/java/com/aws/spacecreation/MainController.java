package com.aws.spacecreation;

import com.aws.spacecreation.interiorboard.InteriorBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final InteriorBoardService interiorBoardservice;
    @GetMapping("/")
    public String root(Model model)
    {
        model.addAttribute("InteriorBoard", interiorBoardservice.getAllInteriorBoards());
        return "view/index";
    }
    @GetMapping("/index")
    public String index() {return "redirect:/";}
    @GetMapping("/about")
    public String about() {
        return "view/about";
    }
}
