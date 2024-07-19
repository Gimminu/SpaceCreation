package com.aws.spacecreation.answer;

import java.time.LocalDateTime;

import com.aws.spacecreation.review.Infomation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Answer {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

@Column(columnDefinition = "TEXT")
private String content;

private LocalDateTime createDate;

@ManyToOne
private Infomation infomation;
}
