package com.aws.spacecreation.review;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aws.spacecreation.DataNotFoundException;
import com.aws.spacecreation.S3Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

	@Autowired
	private final QuestionRepository questionRepoisitory;
		
	@Autowired
	private S3Service s3Service;
	 public Question getQuestion(Integer id) {  
	        Optional<Question> question = this.questionRepoisitory.findById(id);
	        if (question.isPresent()) {
	            return question.get();
	        } else { 
	            throw new DataNotFoundException("question not found");
	        }
	    }
	    
	    
	    public void create(Question question, MultipartFile[] files) throws IOException {

			for(int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				UUID uuid = UUID.randomUUID();
				String fileName = uuid + "_" + file.getOriginalFilename();
				s3Service.uploadmanyFiles(file);
				switch(i) {
				case 0:
					question.setImage1(fileName);
					break;
				case 1:
					question.setImage2(fileName);
					break;
				case 2:
					question.setImage3(fileName);
					break;
				}
			}
			question.setCreateDate(LocalDateTime.now());
			this.questionRepoisitory.save(question);
	    }

	
	public void delete(Integer id) {
		questionRepoisitory.deleteById(id);
	}
	
	 	
}
