package com.aws.spacecreation.like;


import com.aws.spacecreation.interiorboard.InteriorBoard;
import com.aws.spacecreation.user.SiteUser;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "board_like")
public class BoardLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private InteriorBoard board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private SiteUser user;

    // getters and setters
}
