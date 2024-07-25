package com.aws.spacecreation.interiorboard;

import com.aws.spacecreation.S3Service;
import com.aws.spacecreation.like.LikeRepository;
import com.aws.spacecreation.like.LikeService;
import com.aws.spacecreation.user.SiteUser;
import com.aws.spacecreation.user.UserRepository;
import com.aws.spacecreation.user.UserSecuritySerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class InteriorBoardService {
    private final InteriorBoardRepository interiorBoardRepository;
    private final S3Service s3Service;
    private final LikeService likeService;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final UserSecuritySerivce  userSecurityService;

    public InteriorBoard getInteriorBoard(Integer id) {
        Optional<InteriorBoard> interiorBoard = this.interiorBoardRepository.findById(id);
        if (interiorBoard.isPresent()) {
            return interiorBoard.get();
        } else {
            throw new DataNotFoundException("board not found");
        }
    }

    public void create(InteriorBoard interiorBoard) {
        interiorBoard.setCreateDate(LocalDateTime.now()); // 등록 시간을 현재 시간으로 설정
        interiorBoard.setViewCount(0);
        interiorBoard.setPoster(userSecurityService.getAuthen().getNickname());
        interiorBoard.setUser(userSecurityService.getAuthen());
        interiorBoardRepository.save(interiorBoard);
    }
    public List<InteriorBoard> getAllInteriorBoards() {
        List<InteriorBoard> interiorBoards = this.interiorBoardRepository.findAll();
        return interiorBoards;
    }

    public void deleteInteriorBoard(Integer id) {
        Optional<InteriorBoard> optionalBoard = interiorBoardRepository.findById(id);
        if (optionalBoard.isPresent()) {
            InteriorBoard board = optionalBoard.get();
            List<String> imageUrls = board.getImageUrls();
            if (imageUrls != null) {
                for (String imageUrl : imageUrls) {
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        try {
                            s3Service.deleteFile(imageUrl);
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to delete image: " + imageUrl, e);
                        }
                    }
                }
            }
            interiorBoardRepository.deleteById(id);
        } else {
            throw new DataNotFoundException("Board not found");
        }
    }

    public List<InteriorBoard> readlist() {
        return interiorBoardRepository.findAll();
    }

    public InteriorBoard read(Integer id) {
        return interiorBoardRepository.findById(id).orElse(null);
    }

    public Optional<InteriorBoard> findById(Integer id) {
        return interiorBoardRepository.findById(id);
    }

    public void increaseViews(Integer boardId) {
        Optional<InteriorBoard> boardOptional = interiorBoardRepository.findById(boardId);
        if (boardOptional.isPresent()) {
            InteriorBoard board = boardOptional.get();
            board.setViewCount(board.getViewCount() + 1);
            interiorBoardRepository.save(board);
        } else {
            throw new DataNotFoundException("Board not found");
        }
    }

    // 좋아요 여부 확인 로직
    public boolean isLikedByCurrentUser(Long userId, InteriorBoard board) {
        if (userId != null) {
            SiteUser user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                return likeService.isLikedByUser(board, user);
            }
        }
        return false;
    }

    // 좋아요 토글 로직
    public void toggleLike(Long userId, Integer boardId) {
        if (userId != null) {

            SiteUser user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                InteriorBoard board = interiorBoardRepository.findById(boardId).orElse(null);
                if (board != null) {
                	System.out.println("인테리어서비스까진 넘어옴");
                    likeService.toggleLike(board, user);
                }
            }
        }
    }


    public List<InteriorBoard> getBoardsSortedByDate() { //최신순
        return interiorBoardRepository.findAllByOrderByCreateDateDesc();
    }

    public List<InteriorBoard> getBoardsSortedByViews() { //조회순
        return interiorBoardRepository.findAllByOrderByViewCountDesc();
    }

    public List<InteriorBoard> getBoardsSortedByLikes() { //인기순
        return interiorBoardRepository.findAllByOrderByLikesDesc();
    }

    //수정
    public void updatePost(Integer id, InteriorBoard updatedBoard) {
        Optional<InteriorBoard> existingBoardOpt = interiorBoardRepository.findById(id);
        if (existingBoardOpt.isPresent()) {
            InteriorBoard existingBoard = existingBoardOpt.get();
            existingBoard.setSubject(updatedBoard.getSubject());
            existingBoard.setContent(updatedBoard.getContent());
            existingBoard.setImageUrls(updatedBoard.getImageUrls());
            interiorBoardRepository.save(existingBoard);
        } else {
            throw new DataNotFoundException("Board not found");
        }
    }

}
