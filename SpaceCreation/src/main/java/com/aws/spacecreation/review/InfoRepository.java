package com.aws.spacecreation.review;

import org.springframework.data.jpa.repository.JpaRepository;
public interface InfoRepository extends JpaRepository<Infomation, Integer> {
	Infomation findBySubject(String subject);
	Infomation findBySubjectAndContent(String subject, String content);
}
