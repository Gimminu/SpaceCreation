package com.aws.spacecreation.like;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aws.spacecreation.interiorboard.InteriorBoard;

public interface LikeRepository extends JpaRepository<Likes, Integer> {
	Optional<Likes> findByInteriorBoardAndUsername(InteriorBoard interiorBoard, String username);
}
