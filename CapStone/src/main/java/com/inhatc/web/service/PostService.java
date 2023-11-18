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
import com.inhatc.web.entity.TipComment;
import com.inhatc.web.entity.TipPost;
import com.inhatc.web.repository.MemberRepository;
import com.inhatc.web.repository.RequestCommentRepository;
import com.inhatc.web.repository.RequestPostRepository;
import com.inhatc.web.repository.TipCommentRepository;
import com.inhatc.web.repository.TipPostRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

	private final RequestPostRepository requestPostRepository;
	
	private final TipPostRepository tipPostRepository;
	
	private final RequestCommentRepository requestCommentRepository;
	
	private final TipCommentRepository tipCommentRepository;
	
	private final MemberRepository memberRepository;
	
	public Page<RequestPost> requestPaging(int page) {
		Pageable pageable = PageRequest.of(page, 20);
		Page<RequestPost> requestPost = requestPostRepository.findAll(pageable);
		
		for (RequestPost post : requestPost) {
			Hibernate.initialize(post.getMemberDetail());
			post.setUploaderNickname();
		}
		
		return this.requestPostRepository.findAll(pageable);
	}
	
	public Page<TipPost> tipPaging(int page) {
		Pageable pageable = PageRequest.of(page, 20);
		Page<TipPost> tipPost = tipPostRepository.findAll(pageable);
		
		for (TipPost post : tipPost) {
			Hibernate.initialize(post.getMemberDetail());
			post.setUploaderNickname();
		}
		
		return this.tipPostRepository.findAll(pageable);
	}
	
	
	public void saveRequestPost(@LoginUser SessionUser user, String title, String content) throws Exception {
		
		Member member = memberRepository.findByLoginId(user.getLoginId()).get();
		
		RequestPost requestPost = new RequestPost();
		requestPost.setMember(member);
		
		requestPost.setTitle(title);
		requestPost.setContent(content);
		
		requestPostRepository.save(requestPost);
	}
	
	public void saveTipPost(@LoginUser SessionUser user, String title, String content) throws Exception {
		
		Member member = memberRepository.findByLoginId(user.getLoginId()).get();
		
		TipPost tipPost = new TipPost();
		tipPost.setMember(member);
		
		tipPost.setTitle(title);
		tipPost.setContent(content);
		
		tipPostRepository.save(tipPost);
	}

	public List<RequestPost> getRequestList(int page) {
		Pageable pageable = PageRequest.of(page, 20);
		
		List<RequestPost> requestPostList = requestPostRepository.findAllByOrderByRegTimeDescAndIdAsc(pageable);
		
		for (RequestPost post : requestPostList) {
			//Hibernate.initialize(post.getMemberDetail());
			post.setUploaderNickname();
		}
		
		return requestPostList;
	}
	
	public List<TipPost> getTipList(int page) {
		Pageable pageable = PageRequest.of(page, 20);
		
		List<TipPost> tipPostList = tipPostRepository.findAllByOrderByRegTimeDescAndIdAsc(pageable);
		
		for (TipPost post : tipPostList) {
			Hibernate.initialize(post.getMemberDetail());
			post.setUploaderNickname();
		}
		
		return tipPostList;
	}
	
	
	public RequestPost getRequestPost(long postId) {
		
		RequestPost requestPost = requestPostRepository.findById(postId);
		
		requestPost.setUploaderImg();
		requestPost.setUploaderNickname();
		return requestPost;
	}
	
	public TipPost getTipPost(long postId) {
		
		TipPost tipPost = tipPostRepository.findById(postId);
		
		tipPost.setUploaderImg();
		tipPost.setUploaderNickname();
		return tipPost;
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
	
	public List<TipComment> getTipComment(long postId, int page) {
		Pageable pageable = PageRequest.of(page, 30);
		
		List<TipComment> tipCommentList = tipCommentRepository.findByTipPost_IdOrderByRegTimeAsc(postId, pageable);
		
		for (TipComment comment : tipCommentList) {
			comment.setUploaderNickname();
			comment.setUploaderImg();
		}
		
		return tipCommentList;
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
	
	public void saveTipComment(@LoginUser SessionUser user, String comment, long postId) {
		
		Member member = memberRepository.findByLoginId(user.getLoginId()).get();
		TipPost tipPost = tipPostRepository.findById(postId);
		
		TipComment tipComment = new TipComment();
		tipComment.setMember(member);
		tipComment.setTipPost(tipPost);
		tipComment.setComment(comment);
		
		tipCommentRepository.save(tipComment);
		
	}	
	
	public void updateRequestView(long postId) {
		
		RequestPost requestPost = requestPostRepository.findById(postId);
		
		requestPost.setView(requestPost.getView() + 1);
		
		requestPostRepository.save(requestPost).getView();
	}
	
	public void updateTipView(long postId) {
		
		TipPost tipPost = tipPostRepository.findById(postId);
		
		tipPost.setView(tipPost.getView() + 1);
		
		tipPostRepository.save(tipPost).getView();
	}
}
