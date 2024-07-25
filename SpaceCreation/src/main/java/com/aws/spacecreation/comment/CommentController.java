package com.aws.spacecreation.comment;

import com.aws.spacecreation.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aws.spacecreation.user.SiteUser;
import com.aws.spacecreation.user.UserSecuritySerivce;
@RequestMapping("/comment")
@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;
	@Autowired
    private UserSecuritySerivce userSecurityService;

	
	
	@PostMapping("/addComment")
	public String addComment(@RequestParam("boardId") Integer boardId, @RequestParam("content") String content, Model model) {
	    SiteUser siteUser = userSecurityService.getAuthen();
	    
	    if (siteUser == null) {
	        // 인증되지 않은 사용자는 로그인 페이지로 리디렉션
	        return "redirect:/user/login";
	    }
	    
	    Long userId = siteUser.getId();
	    commentService.addComment(boardId, userId, content);

	    return "redirect:/interiorboard/interiorboarddetail/" + boardId;
	}
	
	
	 @PostMapping("/deleteComment")
	    public String deleteComment(@RequestParam("commentId") Long commentId, @RequestParam("boardId") Integer boardId) {
	    	commentService.deleteComment(commentId);
	        return "redirect:/interiorboard/interiorboarddetail/" + boardId;
	    }
}
