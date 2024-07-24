package com.aws.spacecreation.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/notice")
@Controller
public class NoticeController {
	
	@GetMapping("/list")
	public String notice(@RequestParam(required = false, defaultValue = "1",value="mode") String mode,
			@RequestParam(required = false, defaultValue = "", value = "keyword") String kw,
			@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
			@RequestParam(required = false, defaultValue = "id", value = "orderby") String ordered, Pageable pageable,
			Model model) {
		Page<Notice> posts = NoticeService.noticelist(pageable, pageNo, ordered,mode, kw);
		String order_vw="id";

		switch (ordered) {
		case "viewed":
			order_vw = "조회수 순";
			break;
		case "id":
			order_vw = "최신순";
			break;
		}
		
		
		model.addAttribute("posts", posts);
		model.addAttribute("keyword", kw);
		model.addAttribute("orderby", ordered);
		model.addAttribute("ordered", order_vw);
		return"view/notice/notice";
	}
	
	@GetMapping("/create")
	public String notice_create() {
		
		
		
		return "view/notice/create";
	}
	@PostMapping
	public String notice_create(Model model,
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam("file") MultipartFile file) {
		
		
		
		model.addAttribute("posts",posts);
		return"redirect:/notice";
	}
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/update")
	public String notice_update() {
		
		
		
		return "view/notice/update";
	}
	
	
	
}
