package com.aws.spacecreation.interiorboard;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InteriorBoardRepository  extends JpaRepository<InteriorBoard, Integer> {

/*
    List<Question> findBySubjectLike(String kw);
*/
	  List<InteriorBoard> findAllByOrderByCreateDateDesc();
	    List<InteriorBoard> findAllByOrderByViewCountDesc();
	    List<InteriorBoard> findAllByOrderByLikesDesc();

}