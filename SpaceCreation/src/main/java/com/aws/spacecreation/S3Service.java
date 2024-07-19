package com.aws.spacecreation;

import com.amazonaws.services.s3.AmazonS3;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
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
    public void UploadFile(MultipartFile multipartFile, String filename) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        // 현재 서버에 임시 저장
        try (FileOutputStream fos = new FileOutputStream(file)){
            fos.write(multipartFile.getBytes());
        }
        // UUID 적용 버전, UUID가 적용된 파일 이름을 가져와서 사용
        //String filename = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();

        // AWS 전송
        amazonS3.putObject(new PutObjectRequest(bucketName, filename, file));
        //임시 저장된 파일 삭제
        file.delete();
    }
    //다중 파일 업로드 (임시 파일 저장 시스템이 아닌 메타 데이터 저장방식 이용)
    public String uploadmanyFiles(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            throw new IllegalStateException("빈 파일은 업로드 할 수 없습니다.");
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String filename = UUID.randomUUID() + fileExtension; // UUID와 확장자를 조합한 파일 이름

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(new PutObjectRequest(bucketName, filename, multipartFile.getInputStream(), metadata));
        return filename; // 생성된 고유 파일 이름을 반환(UUID추가된 파일)
    }

	 public String uploadFile(MultipartFile multipartFile, String fileName) throws IOException {
	        // Create a temporary file in the local system
	        File file = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);

	        try (FileOutputStream fos = new FileOutputStream(file)) {
	            fos.write(multipartFile.getBytes());
	        }

	        // Upload to AWS S3
	        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file));

	        // Delete the temporary file
	        file.delete();

	        // Return the file URL
	        return amazonS3.getUrl(bucketName, fileName).toString();
	    }


}
