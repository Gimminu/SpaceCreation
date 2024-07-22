package com.aws.spacecreation.interiorboard;

import com.aws.spacecreation.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class InteriorBoardService {


    private final S3Service s3Service;

    private final InteriorBoardRepository interiorBoardRepository;

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
    public List<InteriorBoard> getAllInteriorBoards() {
        List<InteriorBoard> interiorBoards = this.interiorBoardRepository.findAll();
        return interiorBoards;
    }



    public void delete(Integer id) {
        interiorBoardRepository.deleteById(id);
    }

    public Page<InteriorBoard> readlist(Pageable pageable, Integer pageNo, String ordered, String kw) {
        pageable = PageRequest.of(pageNo, 12, Sort.by(Sort.Direction.DESC, ordered));
        Page<InteriorBoard> page = interiorBoardRepository.findBySubjectLike(pageable,"%" + kw + "%");
        return page;
    }
}
