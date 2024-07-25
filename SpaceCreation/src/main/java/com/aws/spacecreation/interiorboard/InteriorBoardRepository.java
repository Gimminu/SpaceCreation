package com.aws.spacecreation.interiorboard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InteriorBoardRepository extends JpaRepository<InteriorBoard, Integer> {
    Page<InteriorBoard> findAllByOrderByCreateDateDesc(Pageable pageable);
    Page<InteriorBoard> findAllByOrderByViewCountDesc(Pageable pageable);

    @Query("SELECT b FROM InteriorBoard b LEFT JOIN b.likes l GROUP BY b.id ORDER BY COUNT(l) DESC")
    Page<InteriorBoard> findAllOrderByLikesCountDesc(Pageable pageable);
}
