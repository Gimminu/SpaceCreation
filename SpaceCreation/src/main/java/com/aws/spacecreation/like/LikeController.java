package com.aws.spacecreation.like;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.aws.spacecreation.interiorboard.InteriorBoard;
import com.aws.spacecreation.interiorboard.InteriorBoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class LikeController {
	
	private final LikeService likeService;
	private final InteriorBoardService interiorBoardService;
	
	@PostMapping("/like/{id}")
	public void like(Model model, @PathVariable("id") Integer id) {
		InteriorBoard interiorBoard = this.interiorBoardService.getInteriorBoard(id);
		likeService.like(interiorBoard);
	}
	
	
}
