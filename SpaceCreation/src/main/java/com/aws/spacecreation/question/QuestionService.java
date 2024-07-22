package com.aws.spacecreation.question;

import com.aws.spacecreation.interiorboard.DataNotFoundException;
import com.aws.spacecreation.user.SiteUser;
import com.aws.spacecreation.user.UserRole;
import com.aws.spacecreation.user.UserSecuritySerivce;
import com.aws.spacecreation.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class QuestionService {

    private final EmailService emailService;
    private final QuestionRepository questionRepository;
    private final JavaMailSender mailSender;
    private final UserSecuritySerivce userSecuritySerivce;
    private final UserService userService;

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
        question.setUser(userSecuritySerivce.getauthen());
        question.setCreateDate(LocalDateTime.now());
        questionRepository.save(question);
    }

    @Transactional
    public void delete(Integer id) {
        SiteUser siteuser = userSecuritySerivce.getauthen();
        if(siteuser.equals(questionRepository.findById(id).get().getUser())||siteuser.getUserRole().equals(UserRole.ADMIN)){
            questionRepository.deleteById(id);
        }
        else{
            throw new SecurityException("게시물의 작성자 혹은 관리자만 지울 수 있습니다.");
        }

    }

    // 조회수 증가 메서드
    private void increaseViews(Question question) {
        int views = question.getViews();
        question.setViews(views + 1);
        questionRepository.save(question);
    }

    public void update(Integer id, Question question){
        Optional<Question> questionOpt = questionRepository.findById(id);
        Question existQuestion = questionOpt.get();
        existQuestion.setSubject(question.getSubject());
        existQuestion.setContent(question.getContent());
        existQuestion.setUpdateDate(LocalDateTime.now());
        questionRepository.save(existQuestion);
    }
}
