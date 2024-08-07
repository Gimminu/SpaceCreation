package com.aws.spacecreation.like;

import com.aws.spacecreation.interiorboard.InteriorBoard;
import com.aws.spacecreation.user.SiteUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;

    @Transactional
    public void toggleLike(InteriorBoard board, SiteUser user) {
        if (likeRepository.existsByBoardAndUser(board, user)) { //존재하면
            likeRepository.deleteByBoardAndUser(board, user);  //삭제
            System.out.println("좋아요 취소");
        } else { //없으면 생성
            BoardLike like = new BoardLike();
            like.setBoard(board);
            like.setUser(user);
            likeRepository.save(like);
            System.out.println("좋아요");
        }
    }
    public boolean isLikedByUser(InteriorBoard board, SiteUser user) {
        return likeRepository.existsByBoardAndUser(board, user);
    }

    public int getLikeCount(InteriorBoard board) {
        return likeRepository.countByBoard(board);
    }
}
