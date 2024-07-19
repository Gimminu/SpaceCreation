package com.aws.spacecreation.review;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aws.spacecreation.interiorboard.DataNotFoundException;
import com.aws.spacecreation.S3Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InforService {

	@Autowired
	private final InforRepository infoRepoisitory;
		
	@Autowired
	private S3Service s3Service;
	 public Information getQuestion(Integer id) {
	        Optional<Information> infomation = this.infoRepoisitory.findById(id);
	        if (infomation.isPresent()) {
	            return infomation.get();
	        } else { 
	            throw new DataNotFoundException("infomation not found");
	        }
	    }
	    
	    
	    public void create(Information information, MultipartFile[] files) throws IOException {

			for(int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				UUID uuid = UUID.randomUUID();
				String fileName = uuid + "_" + file.getOriginalFilename();
				s3Service.uploadmanyFiles(file);
				switch(i) {
				case 0:
					information.setImage1(fileName);
					break;
				case 1:
					information.setImage2(fileName);
					break;
				case 2:
					information.setImage3(fileName);
					break;
				}
			}
			information.setCreateDate(LocalDateTime.now());
			this.infoRepoisitory.save(information);
	    }

	
	public void delete(Integer id) {
		infoRepoisitory.deleteById(id);
	}
	
	 	
}
