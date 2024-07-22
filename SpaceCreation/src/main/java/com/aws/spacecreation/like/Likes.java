package com.aws.spacecreation.like;

import com.aws.spacecreation.interiorboard.InteriorBoard;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Likes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	private String username;
	
	@ManyToOne
	private InteriorBoard interiorBoard;
	
	
}
