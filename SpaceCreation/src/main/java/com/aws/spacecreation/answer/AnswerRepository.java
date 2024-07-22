package com.aws.spacecreation.answer;

import org.springframework.data.jpa.repository.JpaRepository;
// answerRepository 추가
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
