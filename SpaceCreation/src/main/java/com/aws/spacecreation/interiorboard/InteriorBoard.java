package com.aws.spacecreation.interiorboard;

import com.aws.spacecreation.comment.Comment;
import com.aws.spacecreation.user.SiteUser;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.aws.spacecreation.comment.Comment;
import com.aws.spacecreation.like.BoardLike;
import com.aws.spacecreation.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private LocalDateTime createDate;

    @Column
    private List<String> imageUrls; // 다중 파일 URL을 저장

    private Integer viewCount = 0; // 조회수를 저장하는 필드, 기본값 0

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardLike> likes = new ArrayList<>();

    @ManyToOne
    private SiteUser user;

    @OneToMany(mappedBy = "interiorBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @Override
    public String toString() {
        return "InteriorBoard{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", imageUrls=" + imageUrls +
                ", viewCount=" + viewCount +
                ", user=" + (user != null ? user.getUsername() : "null") +
                ", comments=" + comments.size() +
                ", likes=" + likes.size() +
                '}';
    }
}