package com.aws.spacecreation.interiorboard;

import java.time.LocalDateTime;
import java.util.List;

import com.aws.spacecreation.interiorboardreply.InteriorBoardReply;
import com.aws.spacecreation.like.Likes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class InteriorBoard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 200)
    private String poster;

	@Column(length = 200)
	private String subject;

	@Column(columnDefinition = "TEXT")
	private String content;

	private Integer viewed;

	private Integer likes;

	private LocalDateTime createDate;

	private String image1;

	@OneToMany(mappedBy = "interiorBoard", cascade = CascadeType.REMOVE)
	private List<InteriorBoardReply> replyList;

	@OneToMany(mappedBy = "interiorBoard", cascade = CascadeType.REMOVE)
	private List<Likes> likelist;

//	@ManyToOne
//	private SiteUser user;

}