package com.aws.spacecreation.interiorboard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class InteriorBoardController {
    
    private final InteriorBoardService interiorBoardService;

    
    @GetMapping("/")
    public String index() {
    	return "index111";
    }
    
    
    @GetMapping("/interiorboardlist/page={pageNo}&orderby={orderCriteria}") //게시물 리스트
    public String list(Model model,
    		@RequestParam(required = false,value="kw") String kw,
    		@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
            @RequestParam(required = false, defaultValue = "id", value = "orderby") String ordered,
            Pageable pageable) {
        Page<InteriorBoard> boards = interiorBoardService.readlist(pageable,pageNo,ordered,kw);
        model.addAttribute("boards", boards);
        return "interiorboard/interiorboardlist";
    }

    @GetMapping("/interiorboarddetail") //게시물 상세
    public String detail() {
        return "interiorboard/interiorboarddetail";
    }

    @GetMapping("/interiorboardform")
    public String create(Model model) { //게시물 등록
        return "interiorboard/interiorboardform";
    }

    @PostMapping("/interiorboardform")  //게시물 등록
    public String create(InteriorBoard interiorBoard, @RequestParam("image") MultipartFile file1) {
        interiorBoardService.create(interiorBoard, file1);
        return "redirect:/interiorboardlist"; 
    }
    

}
