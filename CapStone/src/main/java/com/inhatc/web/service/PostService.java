package com.inhatc.web.service;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inhatc.web.config.auth.LoginUser;
import com.inhatc.web.dto.SessionUser;
import com.inhatc.web.entity.Member;
import com.inhatc.web.entity.RequestComment;
import com.inhatc.web.entity.RequestPost;
import com.inhatc.web.repository.MemberRepository;
import com.inhatc.web.repository.RequestCommentRepository;
import com.inhatc.web.repository.RequestPostRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

	private final RequestPostRepository requestPostRepository;
	
	private final RequestCommentRepository requestCommentRepository;
	
	private final MemberRepository memberRepository;
	
	public Page<RequestPost> paging(int page) {
		Pageable pageable = PageRequest.of(page, 20);
		Page<RequestPost> requestPost = requestPostRepository.findAll(pageable);
		
		for (RequestPost post : requestPost) {
			Hibernate.initialize(post.getMemberDetail());
			post.setUploaderNickname();
		}
		
		return this.requestPostRepository.findAll(pageable);
	}
	
	public void saveRequestPost(@LoginUser SessionUser user, String title, String content) throws Exception {
		
		Member member = memberRepository.findByLoginId(user.getLoginId()).get();
		
		RequestPost requestPost = new RequestPost();
		requestPost.setMember(member);
		
		requestPost.setTitle(title);
		requestPost.setContent(content);
		
		requestPostRepository.save(requestPost);
	}
	
	public List<RequestPost> getRequestList(int page) {
		Pageable pageable = PageRequest.of(page, 20);
		
		List<RequestPost> requestPostList = requestPostRepository.findAllByOrderByRegTimeDescAndIdAsc(pageable);
		
		for (RequestPost post : requestPostList) {
			Hibernate.initialize(post.getMemberDetail());
			post.setUploaderNickname();
		}
		
		return requestPostList;
	}
	
	public RequestPost getRequestPost(long postId) {
		
		RequestPost requestPost = requestPostRepository.findById(postId);
		
		requestPost.setUploaderImg();
		requestPost.setUploaderNickname();
		return requestPost;
	}
	
	public List<RequestComment> getRequestComment(long postId, int page) {
		Pageable pageable = PageRequest.of(page, 30);
		
		List<RequestComment> requestCommentList = requestCommentRepository.findByRequestPost_IdOrderByRegTimeAsc(postId, pageable);
		
		for (RequestComment comment : requestCommentList) {
			comment.setUploaderNickname();
			comment.setUploaderImg();
		}
		
		return requestCommentList;
	}
	public void saveRequestComment(@LoginUser SessionUser user, String comment, long postId) {
		
		Member member = memberRepository.findByLoginId(user.getLoginId()).get();
		RequestPost requestPost = requestPostRepository.findById(postId);
		
		RequestComment requestComment = new RequestComment();
		requestComment.setMember(member);
		requestComment.setRequestPost(requestPost);
		requestComment.setComment(comment);
		
		requestCommentRepository.save(requestComment);
		
	}
	
	public void updateView(long postId) {
		
		RequestPost requestPost = requestPostRepository.findById(postId);
		
		requestPost.setView(requestPost.getView() + 1);
		
		requestPostRepository.save(requestPost).getView();
	}
}
