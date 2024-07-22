package com.aws.spacecreation.interiorboard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteriorBoardRepository  extends JpaRepository<InteriorBoard, Integer> {

	Page<InteriorBoard> findBySubjectLike(Pageable pageable, String kw);
/*
    List<Infomation> findBySubjectLike(String kw);
*/


}