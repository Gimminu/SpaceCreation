package com.aws.spacecreation.reviewreply;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReviewReplyController {
	
	@PostMapping("/review/reply/create")
	public String create() {
		
		return"redirect:/review/${review.id}";
	}
	
	
	
	
	
	
	
}
