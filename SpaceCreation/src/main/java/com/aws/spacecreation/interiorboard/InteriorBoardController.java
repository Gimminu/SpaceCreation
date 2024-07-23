package com.aws.spacecreation.interiorboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aws.spacecreation.S3Service;
import com.aws.spacecreation.comment.Comment;
import com.aws.spacecreation.comment.CommentService;
import com.aws.spacecreation.like.LikeService;
import com.aws.spacecreation.user.SiteUser;
import com.aws.spacecreation.user.UserRepository;
import com.aws.spacecreation.user.UserSecuritySerivce;

import jakarta.servlet.http.HttpSession;

@Controller
public class InteriorBoardController {

    @Autowired
    private InteriorBoardService interiorBoardService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private S3Service s3Service;
    
    @Autowired
    private UserSecuritySerivce userSecurityService;

    @Autowired
    private CommentService commentService;

    // 홈 페이지
    @GetMapping("/")
    public String index() {
        return "index111";
    }

    // 테스트 페이지
    @GetMapping("/test")
    public String test() {
        return "test";
    }

//    // 모든 인테리어 게시판 목록 보기
//    @GetMapping("/interiorboardlist")
//    public String list(Model model) {
//        List<InteriorBoard> boards = interiorBoardService.readlist();
//        model.addAttribute("boards", boards);
//        return "interiorboard/interiorboardlist";
//    }

 // 특정 인테리어 게시판의 상세 보기
    @GetMapping("/interiorboarddetail/{id}")
    public String detail(@PathVariable("id") Integer boardId, Model model) {
        interiorBoardService.increaseViews(boardId);
        
        SiteUser siteUser = userSecurityService.getAuthen();  //누군지 확인
        InteriorBoard board = interiorBoardService.read(boardId); 

        Long userId = null;
        boolean isLiked = false;
        if (siteUser != null) {
            userId = siteUser.getId();
            isLiked = interiorBoardService.isLikedByCurrentUser(userId, board); // 현재 유저가 좋아요 했는지 확인
        }else {
        	
        }

        model.addAttribute("board", board);
        
        int likeCount = likeService.getLikeCount(board);
        model.addAttribute("likeCount", likeCount); // 좋아요 수
        model.addAttribute("isLiked", isLiked); 	// 좋아요 상태
        List<Comment> comments = commentService.getCommentsByBoardId(boardId);  // 댓글
        model.addAttribute("comments", comments);

        return "interiorboard/interiorboarddetail";
    }

 // 인테리어 게시판의 좋아요 상태 토글
    @PostMapping("/toggleLike")
    @ResponseBody // 바꾸지마세요
    public String toggleLike(@RequestParam("boardId") Integer boardId, HttpSession session) {
        SiteUser siteUser = userSecurityService.getAuthen(); // 유저정보 받아서

        if (siteUser == null || siteUser.getId() == null) {
            System.out.println("User is not logged in.");
         

            return "failed";
        }

        Long userId = siteUser.getId();

        // 현재 로그인한 유저랑 보드 아이디(확인용)
        System.out.println("toggleLike called with userId: " + userId + " and boardId: " + boardId);

        try {
            interiorBoardService.toggleLike(userId, boardId);
            System.out.println("Like status toggled successfully."); // 확인용
        } catch (Exception e) {
            System.out.println("Error toggling like status: " + e.getMessage()); // 확인용
            return "error";
        }

        return "success";
    }



    // 새 인테리어 게시판 생성 폼 표시
    @GetMapping("/interiorboardform")
    public String createForm() {
        return "interiorboard/interiorboardform";
    }

    // 새 인테리어 게시판 생성
    @PostMapping("/interiorboardform") 
    public String create(InteriorBoard interiorBoard, @RequestParam("imageURL") List<String> imageURLs) {
        interiorBoard.setImageUrls(imageURLs);
        interiorBoardService.create(interiorBoard);
        return "redirect:/interiorboardlist";
    }
    
 

    // 게시물 수정 폼 표시
    @GetMapping("/interiorboardedit/{id}")
    public String showEditForm(@PathVariable("id") Integer boardId, Model model) {
        InteriorBoard board = interiorBoardService.read(boardId);
        model.addAttribute("board", board);
        return "interiorboard/interiorboardedit";
    }

    // 게시물 수정
    @PostMapping("/interiorboardedit/{id}")
    public String update(@PathVariable("id") Integer boardId, @ModelAttribute InteriorBoard board, @RequestParam("imageURL") List<String> imageURLs) {
        board.setImageUrls(imageURLs);
        interiorBoardService.updatePost(boardId, board);
        return "redirect:/interiorboardlist";
    }

    

    // 이미지 업로드 및 URL 반환
    @PostMapping("/uploadImage") 
    @ResponseBody
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String url = s3Service.uploadFile(file);
            return ResponseEntity.ok("{\"url\": \"" + url + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // ID로 인테리어 게시판 삭제
    @GetMapping("/interiorboarddelete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        interiorBoardService.deleteInteriorBoard(id);
        return "redirect:/interiorboardlist";
    }
    
    @GetMapping("/interiorboardlist")
    public String getInteriorBoardList(@RequestParam(value = "sort", required = false, defaultValue = "latest") String sort, Model model) {
        List<InteriorBoard> boards;
        switch (sort) {
            case "views":
                boards = interiorBoardService.getBoardsSortedByViews();
                break;
            case "likes":
                boards = interiorBoardService.getBoardsSortedByLikes();
                break;
            default:
                boards = interiorBoardService.getBoardsSortedByDate();
                break;
        }
        model.addAttribute("boards", boards);
        model.addAttribute("sort", sort);
        return "interiorboard/interiorboardlist";
    }
    
    
    

}
