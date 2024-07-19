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
@RequestMapping("/information")
@Controller
public class InformationController {
	private final InforRepository inforRepository;
	private final InforService inforService;
	
	@Value("${cloud.aws.s3.endpoint}")
	private String downpath;
	
    @GetMapping("/list")
    public String list(Model model) {
        List<Information> informationList = this.inforRepository.findAll();
        model.addAttribute("informationlist", informationList);
        return "view/info/information_list";
    }
    
    //@GetMapping(value = "/question/detail/{id}")
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
    	
        Information information = this.inforService.getQuestion(id);
        model.addAttribute("information", information);
        model.addAttribute("downpath", "https://" + downpath);

        return "view/info/information_detail";
    }
    
    @GetMapping("/create")
    public String infomationCreate() {
        return "view/info/information_form";
    }
    
    @PostMapping("/create")
    public String infomationCreate(@ModelAttribute Information information,
    		@RequestParam ("files") MultipartFile[] files) throws IOException {
    		inforService.create(information, files);
        return "redirect:/information/list"; // 질문 저장후 질문목록으로 이동
    }
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
    	
        inforService.delete(id);

        return "redirect:/information/list"; //  질문목록으로 이동
    }
    
}