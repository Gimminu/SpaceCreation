package com.aws.spacecreation.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aws.spacecreation.interiorboard.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class QuestionService {

    private final EmailService emailService;
    private final QuestionRepository questionRepository;
    private final JavaMailSender mailSender;

    @Transactional(readOnly = true)
    public Page<Question> getAllQuestions(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Question> getAllQuestionsSortedByViews(Pageable pageable) {
        return questionRepository.findAllByOrderByViewsDesc(pageable);
    }


    @Transactional(readOnly = true)
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Transactional
    public Question getQuestion(Integer id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            increaseViews(question);
            return question;
        } else {
            throw new DataNotFoundException("Question not found with id: " + id);
        }
    }

    @Transactional
    public void create(Question question) {
        emailService.sendEmailFromDaum(question);
        question.setCreateDate(LocalDateTime.now());
        questionRepository.save(question);
    }

    @Transactional
    public void delete(Integer id) {
        questionRepository.deleteById(id);
    }

    // 조회수 증가 메서드
    private void increaseViews(Question question) {
        int views = question.getViews();
        question.setViews(views + 1);
        questionRepository.save(question);
    }

    @Transactional
    public void deleteQuestion(Integer id) {
        questionRepository.deleteById(id);
    }
}
