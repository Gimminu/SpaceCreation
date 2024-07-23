package com.aws.spacecreation.question;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	List<Review> findBySubjectLike(String kw);
	List<Review> findBySubject(String kw);
}
