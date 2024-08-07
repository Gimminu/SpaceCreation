package com.aws.spacecreation.notice;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/notice")
@Controller
public class NoticeController {
	
	private final NoticeService noticeService;
	
	@GetMapping("/list")
	public String notice(@RequestParam(required = false, defaultValue = "1",value="mode") String mode,
			@RequestParam(required = false, defaultValue = "", value = "keyword") String kw,
			@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
			@RequestParam(required = false, defaultValue = "id", value = "orderby") String ordered, Pageable pageable,
			Model model) {
		Page<Notice> posts = noticeService.noticelist(pageable, pageNo, ordered,mode, kw);
		String order_vw="id";

		switch (ordered) {
		case "viewed":
			order_vw = "조회수 순";
			break;
		case "id":
			order_vw = "최신순";
			break;
		}
		
		model.addAttribute("selectedMode", mode);
		model.addAttribute("posts", posts);
		model.addAttribute("keyword", kw);
		model.addAttribute("orderby", ordered);
		model.addAttribute("ordered", order_vw);
		return"view/notice/notice_list";
	}
	
	@PostMapping("/list")
	public String list_kw(@RequestParam(required = false, defaultValue = "1",value="mode") String mode,
			@RequestParam(required = false, defaultValue = "", value = "keyword") String kw,
			@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
			@RequestParam(required = false, defaultValue = "id", value = "orderby") String ordered,
			Pageable pageable,	Model model) {
		Page<Notice> posts = noticeService.noticelist(pageable, pageNo, ordered, mode, kw);
		model.addAttribute("selectedMode", mode);
		model.addAttribute("posts", posts);
		model.addAttribute("keyword", kw);
		model.addAttribute("orderby", ordered);
		model.addAttribute("mode", mode);

		return "view/notice/notice_list";

	}
	
	@GetMapping("/create")
	public String notice_create() {
		
		
		
		return "view/notice/notice_create";
	}
	@PostMapping("/create")
	public String notice_create(@ModelAttribute Notice notice,
			@RequestParam("file") MultipartFile file) throws IOException {
		
		notice.setView(0);
		noticeService.create(notice,  file);
		
		
		return"redirect:/notice/list";
	}
	
	@GetMapping("/detail/{id}")
	public String notice_detail(Model model, @PathVariable("id") Integer id) {
		Notice notice = this.noticeService.getNotice(id);
		
		model.addAttribute("notice", notice);
		
		
		return "view/notice/notice_detail";
	}
	
	@GetMapping("/update/{id}")
	public String update(Model model, @PathVariable ("id") Integer id) {
		
		model.addAttribute("Notice", noticeService.getNotice(id));

		return "view/notice/notice_update";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute Notice notice,
			@RequestParam("file") MultipartFile file) throws IOException {
		
		noticeService.create(notice,file);
		return "redirect:/Notice/list";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		noticeService.delete(id);
		return "redirect:/notice/list";
	}
	
	
	
}
