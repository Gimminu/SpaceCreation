package com.aws.spacecreation.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
	Page<Notice> findBySubjectLikeOrPlaincontentLike(Pageable pageable,String kw,String kw2);
	Page<Notice> findBySubjectLike(Pageable pageable,String kw);	
	Page<Notice> findByPlaincontentLike(Pageable pageable,String kw);
}
