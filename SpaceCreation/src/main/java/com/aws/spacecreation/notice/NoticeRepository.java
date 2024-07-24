package com.aws.spacecreation.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
	Page<Notice> findBySubjectLikeOrContentLike(Pageable pageable,String kw,String kw2);
	Page<Notice> findBySubjectLike(Pageable pageable,String kw);	
	Page<Notice> findByContentLike(Pageable pageable,String kw);
}
