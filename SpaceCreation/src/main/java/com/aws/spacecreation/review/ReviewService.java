package com.aws.spacecreation.review;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aws.spacecreation.S3Service;

@Service
public class ReviewService {

	@Autowired
	private S3Service s3Service;
	@Autowired
	private ReviewRepository reviewRepository;

	public void create(Review review, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
		UUID uuid1 = UUID.randomUUID();
		String fileName1 = uuid1 + "_" + file1.getOriginalFilename();
		UUID uuid2 = UUID.randomUUID();
		String fileName2 = uuid2 + "_" + file1.getOriginalFilename();
		UUID uuid3 = UUID.randomUUID();
		String fileName3 = uuid3 + "_" + file1.getOriginalFilename();
		review.setCreateDate(LocalDateTime.now());

		s3Service.UploadFile(file1, fileName1);
		s3Service.UploadFile(file2, fileName2);
		s3Service.UploadFile(file3, fileName3);

		review.setImage3(fileName1);
		review.setImage3(fileName2);
		review.setImage3(fileName3);
		this.reviewRepository.save(review);
	}
}
