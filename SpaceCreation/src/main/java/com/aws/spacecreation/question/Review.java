package com.aws.spacecreation.question;

import com.aws.spacecreation.like.Likes;
import com.aws.spacecreation.reviewreply.ReviewReply;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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
    
    private Integer likes;
    
    private Integer square;
    
    private String region;
    
    
    

    private LocalDateTime createDate;
    
    private String image;
   
    
    @OneToMany(mappedBy="review" , cascade = CascadeType.REMOVE)
    private List<ReviewReply> replyList;
    
    @OneToMany(mappedBy="review" , cascade = CascadeType.REMOVE)
    private List<Likes> likelist;
}
