package com.inhatc.web.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inhatc.web.entity.Music;
import com.inhatc.web.repository.MusicRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageService {

	private final MusicRepository musicRepository;
	
	public List<Music> getUploadMusic(Long memberId) {
		return musicRepository.findByMemberIdOrderByRegTimeDesc(memberId);
	}
}
