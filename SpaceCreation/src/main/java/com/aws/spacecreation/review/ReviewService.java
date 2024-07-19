package com.aws.spacecreation.review;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aws.spacecreation.DataNotFoundException;
import com.aws.spacecreation.S3Service;

@Service
public class ReviewService {

	@Autowired
	private S3Service s3Service;
	@Autowired
	private ReviewRepository reviewRepository;

	public void create(Review review, MultipartFile file)
			throws IOException {
		
		
		UUID uuid1 = UUID.randomUUID();
		String fileName = uuid1 + "_" + file.getOriginalFilename();
		review.setViewed(0);
		review.setLikes(0);
		review.setImage(fileName);
		s3Service.uploadmanyFiles(file);
		
		
		this.reviewRepository.save(review);
	}

	public Review getReview(Integer id) {
		Optional<Review> review = this.reviewRepository.findById(id);
		if (review.isPresent()) {
			Review review1 = review.get();
			review1.setViewed(review1.getViewed()+1);
			this.reviewRepository.save(review1);
			return review1;
		} else {
			throw new DataNotFoundException("review not found");
		}
	}
	
	public void delete(Integer id) {
		reviewRepository.deleteById(id);
	}
	
	public List<Review> searchkw(String kw){
		return reviewRepository.findBySubjectLike("%"+kw+"%");
	}
	
	
}
