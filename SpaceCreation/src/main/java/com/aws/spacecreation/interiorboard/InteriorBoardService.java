package com.aws.spacecreation.interiorboard;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aws.spacecreation.S3Service;

@Service
public class InteriorBoardService {

    @Autowired
    private S3Service s3Service;
    @Autowired
    private InteriorBoardRepository interiorBoardRepository;

    public InteriorBoard getInteriorBoard(Integer id) {
        Optional<InteriorBoard> interiorBoard = this.interiorBoardRepository.findById(id);
        if (interiorBoard.isPresent()) {
            return interiorBoard.get();
        } else {
            throw new DataNotFoundException("board not found");
        }
    }

    public void create(InteriorBoard interiorBoard, MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        try {
            String fileUrl = s3Service.uploadFile(file, fileName);
            interiorBoard.setImage1(fileUrl);
            interiorBoard.setCreateDate(LocalDateTime.now());
            this.interiorBoardRepository.save(interiorBoard);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }
    
    


    public void delete(Integer id) {
        interiorBoardRepository.deleteById(id);
    }

    public List<InteriorBoard> readlist() {
        return interiorBoardRepository.findAll();
    }
}
