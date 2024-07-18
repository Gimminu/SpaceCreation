package com.aws.spacecreation.review;

import java.time.LocalDateTime;
import java.util.List;

import com.aws.spacecreation.like.Like;
import com.aws.spacecreation.reviewreply.ReviewReply;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;
    
    @Column(length = 200)
    private String poster;

    @Column(columnDefinition = "TEXT")
    private String content;
    
    private Integer viewed;
    private Integer like;
    private Integer square;
    private String region;
    
    
    

    private LocalDateTime createDate;
    
    private String image;
   
    
    @OneToMany(mappedBy="review" , cascade = CascadeType.REMOVE)
    private List<ReviewReply> replyList;
    
    @OneToMany(mappedBy="review" , cascade = CascadeType.REMOVE)
    private List<Like> likelist;
}
