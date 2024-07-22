package com.aws.spacecreation.interiorboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/interiorboard")
@Controller
public class InteriorBoardController {
	@Autowired
	private InteriorBoardService interiorBoardService;

	@GetMapping("/list") //게시물 리스트
    public String list(@RequestParam(required = false, defaultValue = "",value="keyword") String kw,
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
            @RequestParam(required = false, defaultValue = "id", value = "orderby") String ordered,
            Pageable pageable,
            Model model) {
        Page<InteriorBoard> boards = interiorBoardService.readlist(pageable, pageNo, ordered, kw);
        model.addAttribute("boards", boards);
        return "view/interiorboard/interiorboardlist";
    }
	
	/*
	 * @GetMapping("/interiorboarddetail") //게시물 상세 public String detail() { return
	 * "view/interiorboard/interiorboarddetail"; }
	 */

	@GetMapping("/interiorboardform")
	public String create(Model model) { // 게시물 등록
		return "view/interiorboard/interiorboardform";
	}

<<<<<<< Updated upstream
	@PostMapping("/interiorboardform") // 게시물 등록
	public String create(InteriorBoard interiorBoard, @RequestParam("image") MultipartFile file1) {
		interiorBoardService.create(interiorBoard, file1);
		return "redirect:/interiorboard/list";
	}
	
=======
    @PostMapping("/interiorboardform")  //게시물 등록
    public String create(InteriorBoard interiorBoard, @RequestParam("image") MultipartFile file1) {
        interiorBoardService.create(interiorBoard, file1);
        return "redirect:/interiorboard/list"; 
    }
    
>>>>>>> Stashed changes

}
