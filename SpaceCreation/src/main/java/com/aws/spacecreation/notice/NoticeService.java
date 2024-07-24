package com.aws.spacecreation.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeRepository noticeRepository;

	public Page<Notice> noticelist(Pageable pageable, Integer pageNo, String ordered, String mode, String kw,
			String poster) {
		pageable = PageRequest.of(pageNo, 1, Sort.by(Sort.Direction.DESC, ordered));
		Page<Notice> page;
		switch (mode) {
		case "1":
			page = noticeRepository.findBySubjectLikeOrContentLike(pageable, "%" + kw + "%", "%" + kw + "%");
			break;
		case "2":
			page = noticeRepository.findBySubjectLike(pageable, "%" + kw + "%");
			break;
		case "3":
			page = noticeRepository.findByContentLike(pageable, "%" + kw + "%");
			break;
		default:
			page = noticeRepository.findAll(pageable); // 기본값 설정
			break;
		}

		return page;
	}
}
