package com.aws.spacecreation;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucketName}")
	private String bucketName;

	private final AmazonS3 amazonS3;

	  public String uploadFile(MultipartFile file) throws IOException {
	        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
	        ObjectMetadata metadata = new ObjectMetadata();
	        metadata.setContentLength(file.getSize());
	        amazonS3.putObject(bucketName, fileName, file.getInputStream(), metadata);
	        return amazonS3.getUrl(bucketName, fileName).toString();
	    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
    public void deleteFile(String fileUrl) {
        if (fileUrl != null && !fileUrl.isEmpty()) {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        }
    }

}
