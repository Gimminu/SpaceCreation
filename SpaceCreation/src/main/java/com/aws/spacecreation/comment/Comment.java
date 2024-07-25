package com.aws.spacecreation.comment;

import java.time.LocalDateTime;

import com.aws.spacecreation.interiorboard.InteriorBoard;
import com.aws.spacecreation.user.SiteUser;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;




@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private InteriorBoard interiorBoard;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private SiteUser user;

    private String content;
    private LocalDateTime createDate;


   

 
}
