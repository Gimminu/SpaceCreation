	package com.aws.spacecreation.interiorboard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteriorBoardRepository  extends JpaRepository<InteriorBoard, Integer> {
	
	Page<InteriorBoard> findBySubjectLikeOrContentLikeAndPosterLike(Pageable pageable,String kw,String kw2, String poster);
	Page<InteriorBoard> findBySubjectLikeAndPosterLike(Pageable pageable,String kw, String poster);	
	Page<InteriorBoard> findByContentLikeAndPosterLike(Pageable pageable,String kw, String poster);
	Page<InteriorBoard> findByPosterLike(Pageable pageable,String kw);
/*
    List<Question> findBySubjectLike(String kw);
*/


}