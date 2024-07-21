package com.aws.spacecreation.interiorboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/interiorboard")
@Controller
public class InteriorBoardController {
    @Autowired
    private InteriorBoardService interiorBoardService;

    @GetMapping("/list") //게시물 리스트
    public String list(Model model) {
        List<InteriorBoard> boards = interiorBoardService.readlist();
        model.addAttribute("boards", boards);
        return "view/interiorboard/interiorboardlist";
    }
    /*
    @GetMapping("/interiorboarddetail") //게시물 상세
    public String detail() {
        return "view/interiorboard/interiorboarddetail";
    }*/

    @GetMapping("/interiorboardform")
    public String create(Model model) { //게시물 등록
        return "view/interiorboard/interiorboardform";
    }

    @PostMapping("/interiorboardform")  //게시물 등록
    public String create(InteriorBoard interiorBoard, @RequestParam("image") MultipartFile file1) {
        interiorBoardService.create(interiorBoard, file1);
        return "redirect:/interiorboardlist"; 
    }
    

}
