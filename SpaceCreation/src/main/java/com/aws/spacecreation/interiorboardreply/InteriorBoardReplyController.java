package com.aws.spacecreation.interiorboardreply;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aws.spacecreation.interiorboard.InteriorBoard;
import com.aws.spacecreation.interiorboard.InteriorBoardService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/reply")
@RequiredArgsConstructor
@Controller
public class InteriorBoardReplyController {
	
	private final InteriorBoardService interiorBoardService;
	
	private final InteriorBoardReplyService interiorBoardreplyService;
	
	@PostMapping("/create/{id}")
	public String create(Model model, @PathVariable("id") Integer id,
//			@RequestParam(value = "username") String username,
			@RequestParam(value = "content") String content)  {
		InteriorBoard interiorBoard = this.interiorBoardService.getInteriorBoard(id);
		interiorBoardreplyService.create(interiorBoard,  content);

		return String.format("redirect:/interiorBoard/detail/%s", id);
	}

}
