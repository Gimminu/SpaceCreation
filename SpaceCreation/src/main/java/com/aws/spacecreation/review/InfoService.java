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
public class InfoService {

	@Autowired
	private final InfoRepository infoRepoisitory;
		
	@Autowired
	private S3Service s3Service;
	 public Infomation getQuestion(Integer id) {
	        Optional<Infomation> infomation = this.infoRepoisitory.findById(id);
	        if (infomation.isPresent()) {
	            return infomation.get();
	        } else { 
	            throw new DataNotFoundException("infomation not found");
	        }
	    }
	    
	    
	    public void create(Infomation infomation, MultipartFile[] files) throws IOException {

			for(int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				UUID uuid = UUID.randomUUID();
				String fileName = uuid + "_" + file.getOriginalFilename();
				s3Service.uploadmanyFiles(file);
				switch(i) {
				case 0:
					infomation.setImage1(fileName);
					break;
				case 1:
					infomation.setImage2(fileName);
					break;
				case 2:
					infomation.setImage3(fileName);
					break;
				}
			}
			infomation.setCreateDate(LocalDateTime.now());
			this.infoRepoisitory.save(infomation);
	    }

	
	public void delete(Integer id) {
		infoRepoisitory.deleteById(id);
	}
	
	 	
}
