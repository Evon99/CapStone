package com.inhatc.web.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inhatc.web.entity.Member;
import com.inhatc.web.entity.Report;
import com.inhatc.web.repository.MemberDetailRepository;
import com.inhatc.web.repository.MemberRepository;
import com.inhatc.web.repository.MusicRepository;
import com.inhatc.web.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

	private final ReportRepository reportRepository;
	
	private final MusicRepository musicRepository;
	
	private final MemberRepository memberRepository;
	
	private final MemberDetailRepository memberDetailRepository;
	
	public void saveReport(long musicId, String reportContent, String nickname) {
		
		Report report = new Report();
		
		Long memberId = memberDetailRepository.findMemberIdByNickname(nickname);
		Member member = memberRepository.findMemberById(memberId);
		
		report.setMusic(musicRepository.findById(musicId));
		report.setReportContent(reportContent);
		report.setMember(member);
		
		reportRepository.save(report);
	}
	
	public Page<Report> getReportList(int page, String filter, String keyword) {
		Pageable pageable = PageRequest.of(page, 6);
		
		Page<Report> paging;
		
		if(filter.equals("title")) {
			paging = reportRepository.findAllByTitleContainingOrderByRegTimeDesc(keyword, pageable);
		} else if (filter.equals("nickname")) {
			paging = reportRepository.findAllByNicknameOrderByRegTimeDesc(keyword, pageable);
		} else {
			paging = reportRepository.findAllByOrderByRegTimeDesc(pageable);
		}
		
		for(Report report : paging) {
			report.setUploaderNickname();
		}
		
		return paging;
		
	}
	
}
