package com.inhatc.web.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inhatc.web.entity.Music;
import com.inhatc.web.repository.MemberMusicStorageRepository;
import com.inhatc.web.repository.MusicRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageService {

	private final MusicRepository musicRepository;
	
	private final MemberMusicStorageRepository memberMusicStorageRepository;
	
	public List<Music> getUploadMusic(Long memberId) {
		return musicRepository.findByMemberIdOrderByRegTimeDesc(memberId);
	}
	
	public List<Music> getAddMusic(Long memberId) {
		List<Long> addMusicId = memberMusicStorageRepository.findMusicIdsByMemberId(memberId);
		System.out.println("추가한 음악:" + addMusicId);
		
		System.out.println("추가한 음악 정보" + musicRepository.findByIdIn(addMusicId));
		return musicRepository.findByIdIn(addMusicId);
	}
}
