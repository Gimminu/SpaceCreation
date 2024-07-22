package com.aws.spacecreation.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);

    Page<Question> findAllByOrderByViewsDesc(Pageable pageable);
}
