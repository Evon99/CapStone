package com.inhatc.web.service;

import org.springframework.stereotype.Service;

import com.inhatc.web.entity.LikedMusic;
import com.inhatc.web.repository.LikedMusicRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final LikedMusicRepository likedMusicRepository;
	

}
