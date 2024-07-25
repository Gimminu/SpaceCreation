package com.aws.spacecreation.notice;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Notice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	private String username;
	
	@Column(length = 200)
	private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;
    
    @Column(columnDefinition = "TEXT")
    private String plaincontent;
    
    private String image1;
    
    private Integer view;
    
    private LocalDateTime createDate;
}
