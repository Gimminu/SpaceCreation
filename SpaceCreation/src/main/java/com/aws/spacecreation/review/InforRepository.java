package com.aws.spacecreation.review;

import org.springframework.data.jpa.repository.JpaRepository;
public interface InforRepository extends JpaRepository<Information, Integer> {
	Information findBySubject(String subject);
	Information findBySubjectAndContent(String subject, String content);
}
