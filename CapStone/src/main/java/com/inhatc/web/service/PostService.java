package com.inhatc.web.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inhatc.web.config.auth.LoginUser;
import com.inhatc.web.dto.SessionUser;
import com.inhatc.web.entity.Member;
import com.inhatc.web.entity.RequestPost;
import com.inhatc.web.repository.MemberRepository;
import com.inhatc.web.repository.RequestPostRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

	private final RequestPostRepository requestPostRepository;
	
	private final MemberRepository memberRepository;
	
	public void saveRequestPost(@LoginUser SessionUser user, String title, String content) throws Exception {
		
		Member member = memberRepository.findByLoginId(user.getLoginId()).get();
		
		RequestPost requestPost = new RequestPost();
		requestPost.setMember(member);
		
		requestPost.setTitle(title);
		requestPost.setContent(content);
		
		requestPostRepository.save(requestPost);
	}
}
