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
@RequestMapping("/info")
@Controller
public class InfomationController {
	private final InfoRepository infoRepository;
	private final InfoService infoService;
	
	@Value("${cloud.aws.s3.endpoint}")
	private String downpath;
	
    @GetMapping("/list")
    public String list(Model model) {
        List<Infomation> infomationList = this.infoRepository.findAll();
        model.addAttribute("infomationlist", infomationList);
        return "view/info/infomation_list";
    }
    
    //@GetMapping(value = "/question/detail/{id}")
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
    	
        Infomation infomation = this.infoService.getQuestion(id);
        model.addAttribute("infomation", infomation);
        model.addAttribute("downpath", "https://" + downpath);

        return "view/info/infomation_detail";
    }
    
    @GetMapping("/create")
    public String infomationCreate() {
        return "view/info/infomation_form";
    }
    
    @PostMapping("/create")
    public String infomationCreate(@ModelAttribute Infomation infomation,
    		@RequestParam ("files") MultipartFile[] files) throws IOException {
    		infoService.create(infomation, files);
        return "redirect:/info/infomation_list"; // 질문 저장후 질문목록으로 이동
    }
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
    	
        infoService.delete(id);

        return "redirect:/info/list"; //  질문목록으로 이동
    }
    
}