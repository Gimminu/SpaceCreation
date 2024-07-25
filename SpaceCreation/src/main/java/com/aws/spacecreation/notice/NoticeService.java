package com.aws.spacecreation.notice;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aws.spacecreation.S3Service;
import com.aws.spacecreation.interiorboard.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {
	
	private final S3Service s3Service;
	private final NoticeRepository noticeRepository;

	public Page<Notice> noticelist(Pageable pageable, Integer pageNo, String ordered, String mode, String kw) {
		pageable = PageRequest.of(pageNo, 10, Sort.by(Sort.Direction.DESC, ordered));
		Page<Notice> page;
		switch (mode) {
		case "1":
			page = noticeRepository.findBySubjectLikeOrPlaincontentLike(pageable, "%" + kw + "%", "%" + kw + "%");
			break;
		case "2":
			page = noticeRepository.findBySubjectLike(pageable, "%" + kw + "%");
			break;
		case "3":
			page = noticeRepository.findByPlaincontentLike(pageable, "%" + kw + "%");
			break;
		default:
			page = Page.empty();
			break;
		}

		return page;
	}
	
	
	
	public void create(Notice notice, MultipartFile file) throws IOException {
		
		Optional<Notice> temp= noticeRepository.findById(notice.getId());
		Notice notice1 = temp.get();
		if(!file.isEmpty())
		notice.setImage1(s3Service.uploadFile(file));
		else {
			notice.setImage1(notice1.getImage1());
		}
		
		String pc = notice.getContent();
		pc = pc.replaceAll("(?i)<br\\s*/?>", "\n");
		pc = pc.replaceAll("(?i)<[^>]*>", "");
		notice.setPlaincontent(pc);
		notice.setCreateDate(LocalDateTime.now());
		noticeRepository.save(notice);	
		
		
		
	}
	
	
	public Notice getNotice(Integer id) {
		Optional<Notice> notice = this.noticeRepository.findById(id);
		if (notice.isPresent()) {
			Notice notice1 = notice.get();
			notice1.setView(notice1.getView()+1);
			this.noticeRepository.save(notice1);
            return notice1;
		} else {
			throw new DataNotFoundException("notice not found");
		}
		
	}
	
	public void delete(Integer id) {
		noticeRepository.deleteById(id);
	}
}
