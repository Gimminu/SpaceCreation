package com.aws.spacecreation.comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aws.spacecreation.interiorboard.InteriorBoard;
import com.aws.spacecreation.interiorboard.InteriorBoardRepository;
import com.aws.spacecreation.user.SiteUser;
import com.aws.spacecreation.user.UserRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private InteriorBoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Comment> getCommentsByBoardId(Integer boardId) {
        return commentRepository.findByInteriorBoardId(boardId);
    }

    public Comment addComment(Integer boardId, Long userId, String content) {
        InteriorBoard interiorBoard = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("Board not found"));
        SiteUser user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        Comment comment = new Comment();
        comment.setInteriorBoard(interiorBoard);
        comment.setUser(user);
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());

        return commentRepository.save(comment);
    }
    
    


    public boolean deleteComment(Long commentId, Long userId) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if (commentOpt.isPresent()) {
            Comment comment = commentOpt.get();
            if (comment.getUser().getId().equals(userId)) {
                commentRepository.delete(comment);
                return true;
            }
        }
        return false;
    }
    
    
    
}

