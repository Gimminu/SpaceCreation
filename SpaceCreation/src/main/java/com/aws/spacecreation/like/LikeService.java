package com.aws.spacecreation.like;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.aws.spacecreation.interiorboard.InteriorBoard;
import com.aws.spacecreation.interiorboard.InteriorBoardRepository;
import com.aws.spacecreation.user.SiteUser;
import com.aws.spacecreation.user.UserSecuritySerivce;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikeService {

	private final LikeRepository likeRepository;
	private final UserSecuritySerivce userSecuritySerivce;
	private final InteriorBoardRepository interiorBoardRepository;

	public void like(InteriorBoard interiorBoard) {
		SiteUser username = userSecuritySerivce.getauthen();
		Optional<Likes> temp = likeRepository.findByInteriorBoardAndUsername(interiorBoard, username.getUsername());
		if (temp.isEmpty()) {
			Likes like = new Likes();
			like.setUsername(username.getUsername());
			like.setInteriorBoard(interiorBoard);
			likeRepository.save(like);
			interiorBoard.setLikes(interiorBoard.getLikes() + 1);
			this.interiorBoardRepository.save(interiorBoard);
		} else {
			Likes liked = temp.get();
			likeRepository.delete(liked);
			interiorBoard.setLikes(interiorBoard.getLikes() - 1);
			this.interiorBoardRepository.save(interiorBoard);
		}

	}

}
