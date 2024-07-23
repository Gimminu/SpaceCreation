package com.aws.spacecreation.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aws.spacecreation.interiorboard.InteriorBoard;
import com.aws.spacecreation.user.SiteUser;

public interface LikeRepository extends JpaRepository<BoardLike, Long> {
    @Query("SELECT COUNT(l) FROM BoardLike l WHERE l.board = ?1")
    int countByBoard(InteriorBoard board);

    boolean existsByBoardAndUser(InteriorBoard board, SiteUser user);

    void deleteByBoardAndUser(InteriorBoard board, SiteUser user);
}
