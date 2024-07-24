package com.aws.spacecreation.interiorboardreply;

import com.aws.spacecreation.interiorboard.InteriorBoard;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class InteriorBoardReply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String username;

	@Column(columnDefinition = "TEXT")
	private String content;

	private LocalDateTime createDate;

	@ManyToOne
	private InteriorBoard interiorBoard;
}
