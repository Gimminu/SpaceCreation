package com.aws.spacecreation.review;

import java.time.LocalDateTime;
import java.util.List;

import com.aws.spacecreation.answer.Answer;
import com.aws.spacecreation.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 200)
	private String subject;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	
	private LocalDateTime createDate;
	
	private String image1;
    private String image2;
    private String image3;
	
    @ManyToOne
    private SiteUser user;
    
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)//question은 외래키
	private List<Answer> answerList;

	
}