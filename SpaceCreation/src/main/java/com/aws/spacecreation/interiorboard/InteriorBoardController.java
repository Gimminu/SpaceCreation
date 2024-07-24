package com.aws.spacecreation.interiorboard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.aws.spacecreation.user.SiteUser;
import com.aws.spacecreation.user.UserSecuritySerivce;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/interiorboard")
@Controller
public class InteriorBoardController {
	
	private final InteriorBoardService interiorBoardService;
	
	private final UserSecuritySerivce usersecurityService;

	@GetMapping("/list") // 게시물 리스트
	public String list(@RequestParam(required = false, defaultValue = "1",value="mode") String mode,
			@RequestParam(required = false, defaultValue = "", value = "keyword") String kw,
			@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
			@RequestParam(required = false, defaultValue = "id", value = "orderby") String ordered,
			@RequestParam(required = false, defaultValue = "", value = "poster") String poster, Pageable pageable,
			Model model) {
		Page<InteriorBoard> boards = interiorBoardService.readlist(pageable, pageNo, ordered,mode, kw,poster);
		String order_vw="id";

		switch (ordered) {
		case "viewed":
			order_vw = "조회순";
			break;
		case "likes":
			order_vw = "인기순";
			break;
		case "id":
			order_vw = "최신순";
			break;
		}
		
		
		model.addAttribute("boards", boards);
		model.addAttribute("keyword", kw);
		model.addAttribute("orderby", ordered);
		model.addAttribute("ordered", order_vw);
		model.addAttribute("poster", poster);
		
		

		return "view/interiorboard/interiorboardlist";
	}
	
	@GetMapping("/list/my")
	public String list_my(@RequestParam(required = false, defaultValue = "1" ,value="mode") String mode,
			@RequestParam(required = false, defaultValue = "", value = "keyword") String kw,
			@RequestParam(required = false, defaultValue = "0", value = "page") Integer pageNo,
			@RequestParam(required = false, defaultValue = "id", value = "orderby") String ordered,
			@RequestParam(required = false, defaultValue = "", value = "poster") String poster, Pageable pageable,
			Model model) {
		
		SiteUser username = usersecurityService.getauthen();
		String nickname = username.getNickname();
		model.addAttribute("poster", nickname);
		Page<InteriorBoard> boards = interiorBoardService.readlist(pageable, pageNo, ordered,mode, kw, nickname);
		String order_vw="id";

		switch (ordered) {
		case "viewed":
			order_vw = "조회순";
			break;
		case "likes":
			order_vw = "인기순";
			break;
		case "id":
			order_vw = "최신순";
			break;
		}
		
		model.addAttribute("boards", boards);
		model.addAttribute("keyword", kw);
		model.addAttribute("orderby", ordered);
		model.addAttribute("ordered", order_vw);
		
		
		

		return "view/interiorboard/interiorboardlist";
	}

	@PostMapping("/list")
	public String list_kw(@RequestParam(required = false, defaultValue = "1",value="mode") String mode,
			@RequestParam(required = false, defaultValue = "", value = "keyword") String kw,
			@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
			@RequestParam(required = false, defaultValue = "id", value = "orderby") String ordered,
			@RequestParam(required = false, defaultValue = "poster", value = "poster") String poster,Pageable pageable,
			Model model) {
		Page<InteriorBoard> boards = interiorBoardService.readlist(pageable, pageNo, ordered, mode, kw, poster);
		model.addAttribute("boards", boards);
		model.addAttribute("keyword", kw);
		model.addAttribute("orderby", ordered);
		model.addAttribute("poster", poster);
		model.addAttribute("mode", mode);

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

	@PostMapping("/interiorboardform") // 게시물 등록
	public String create(InteriorBoard interiorBoard, @RequestParam("image") MultipartFile file1) {
		interiorBoardService.create(interiorBoard, file1);
		return "redirect:/interiorboard/list";
	}

}
