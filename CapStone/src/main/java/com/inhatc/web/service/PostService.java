package com.inhatc.web.service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.inhatc.web.config.auth.LoginUser;
import com.inhatc.web.dto.SessionUser;
import com.inhatc.web.entity.AiComment;
import com.inhatc.web.entity.AiPost;
import com.inhatc.web.entity.Member;
import com.inhatc.web.entity.RequestComment;
import com.inhatc.web.entity.RequestPost;
import com.inhatc.web.entity.TipComment;
import com.inhatc.web.entity.TipPost;
import com.inhatc.web.repository.AiPostCommentRepository;
import com.inhatc.web.repository.AiPostRepository;
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

	@Value("${voiceImgLocation}")
	private String voiceImgLocation;
	
	@Value("${voiceFileLocation}")
	private String voiceFileLocation;
	
	private final RequestPostRepository requestPostRepository;
	
	private final TipPostRepository tipPostRepository;
	
	private final AiPostRepository aiPostRepository;
	
	private final RequestCommentRepository requestCommentRepository;
	
	private final TipCommentRepository tipCommentRepository;
	
	private final AiPostCommentRepository aiPostCommentRepository;
	
	private final MemberRepository memberRepository;
	
	private final FileService fileService;
	
	public Page<RequestPost> requestPostPaging(int page, String filter, String keyword) {
		Pageable pageable = PageRequest.of(page, 20);
		
		Page<RequestPost> requestPostList;
		
		if (filter.equals("title")) {
			 System.out.println("키워드 필터");
	         requestPostList = requestPostRepository.findAllByTitleOrderByRegTimeDescAndIdAsc(keyword, pageable);
	    } else if(filter.equals("nickname")) {
	    	 requestPostList = requestPostRepository.findAllByOrderByRegTimeDescAndIdAsc(pageable);
	    } else {
	    	requestPostList = requestPostRepository.findAllByOrderByRegTimeDescAndIdAsc(pageable);
	    }
		
		
		for (RequestPost post : requestPostList) {
			post.setUploaderNickname();
		}
		
		return requestPostList;
	}
	
	public Page<TipPost> tipPostPaging(int page, String filter, String keyword) {
		Pageable pageable = PageRequest.of(page, 20);
		
		Page<TipPost> tipPostList;
		
		if (filter.equals("title")) {
			 System.out.println("키워드 필터");
	         tipPostList = tipPostRepository.findAllByTitleOrderByRegTimeDescAndIdAsc(keyword, pageable);
	    } else if(filter.equals("nickname")) {
	    	 tipPostList = tipPostRepository.findAllByOrderByRegTimeDescAndIdAsc(pageable);
	    } else {
	    	 tipPostList = tipPostRepository.findAllByOrderByRegTimeDescAndIdAsc(pageable);
	    }
		
		for (TipPost post : tipPostList) {
			post.setUploaderNickname();
		}
		
		return tipPostList;
	}
	
	public Page<AiPost> aiPostPaging(int page, String filter, String keyword) {
		Pageable pageable = PageRequest.of(page, 6);
		
		Page<AiPost> aiPostList;
		
		if (filter.equals("title")) {
			 System.out.println("키워드 필터");
	         aiPostList = aiPostRepository.findAllByTitleOrderByRegTimeDescAndIdAsc(keyword, pageable);
	    } else if(filter.equals("nickname")) {
	    	 aiPostList = aiPostRepository.findAllByOrderByRegTimeDescAndIdAsc(pageable);
	    } else {
	    	 aiPostList = aiPostRepository.findAllByOrderByRegTimeDescAndIdAsc(pageable);
	    }
		
		for (AiPost post : aiPostList) {
			post.setUploaderNickname();
		}
		
		return aiPostList;
	}
	
	
	public void saveRequestPost(@LoginUser SessionUser user, String title, String content) throws Exception {
		
		Member member = memberRepository.findByLoginId(user.getLoginId()).get();
		
		RequestPost requestPost = new RequestPost();
		requestPost.setMember(member);
		
		requestPost.setTitle(title);
		requestPost.setContent(content.replace("\r\n","<br/>"));
		
		requestPostRepository.save(requestPost);
	}
	
	public void saveTipPost(@LoginUser SessionUser user, String title, String content) throws Exception {
		
		Member member = memberRepository.findByLoginId(user.getLoginId()).get();
		
		TipPost tipPost = new TipPost();
		tipPost.setMember(member);
		
		tipPost.setTitle(title);
		tipPost.setContent(content.replace("\r\n","<br/>"));
		tipPostRepository.save(tipPost);
	}
	
	public void saveVoice(@LoginUser SessionUser user, MultipartFile voiceImgFile, MultipartFile voiceFile, String title, String content) throws Exception {
		
		Member member = memberRepository.findByLoginId(user.getLoginId()).get();
		
		AiPost aiPost = new AiPost();
		aiPost.setMember(member);
		aiPost.setTitle(title);
		aiPost.setContent(content.replace("\r\n","<br/>"));
		
		String oriVoiceName = voiceFile.getOriginalFilename(); 
		String voiceName = ""; 
		String voiceUrl = ""; 
		
		String oriImgName = voiceImgFile.getOriginalFilename(); 
		String imgName = ""; 
		String imgUrl = "";
		
		if(!StringUtils.isEmpty(oriVoiceName)){
            voiceName = fileService.uploadFile(voiceFileLocation, oriVoiceName, voiceFile.getBytes());
            voiceUrl = "/voice/file/" + voiceName; // 파일 저장 장소
        }
		
		if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(voiceImgLocation, oriImgName, voiceImgFile.getBytes());
            imgUrl = "/images/voice/img/" + imgName; // 파일 저장 장소
        }
		
		aiPost.updateVoice(voiceName, oriVoiceName, voiceUrl, imgName, oriImgName, imgUrl);
		aiPostRepository.save(aiPost);		
		
	}
	
	public Page<RequestPost> getRequestList(int page, String title) {
		Pageable pageable = PageRequest.of(page, 20);
		
		Page<RequestPost> requestPostList;
		if (title != null && !title.isEmpty()) {
			 System.out.println("키워드 필터");
	         requestPostList = requestPostRepository.findAllByTitleOrderByRegTimeDescAndIdAsc(title, pageable);
	    } else {
	    	 requestPostList = requestPostRepository.findAllByOrderByRegTimeDescAndIdAsc(pageable);
	    }
		
		for (RequestPost post : requestPostList) {
			//Hibernate.initialize(post.getMemberDetail());
			post.setUploaderNickname();
		}
		
		return requestPostList;
	}
	
//	public List<TipPost> getTipList(int page) {
//		Pageable pageable = PageRequest.of(page, 20);
//		
//		List<TipPost> tipPostList = tipPostRepository.findAllByOrderByRegTimeDescAndIdAsc(pageable);
//		
//		for (TipPost post : tipPostList) {
//			Hibernate.initialize(post.getMemberDetail());
//			post.setUploaderNickname();
//		}
//		
//		return tipPostList;
//	}
//	
//	public List<AiPost> getVoiceList(int page) {
//		Pageable pageable = PageRequest.of(page, 6);
//		
//		List<AiPost> aiPostList = aiPostRepository.findAllByOrderByRegTimeDescAndIdAsc(pageable);
//		
//		for (AiPost post : aiPostList) {
//			Hibernate.initialize(post.getMemberDetail());
//			post.setUploaderNickname();
//		}
//		
//		return aiPostList;
//	}
	
	
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
	
	public AiPost getVoicePost(long postId) throws IOException {
		
		AiPost aiPost = aiPostRepository.findById(postId);
		
		aiPost.setUploaderImg();
		aiPost.setUploaderNickname();
		aiPost.setVoiceFileSize(getVoiceFileSize(aiPost.getVoiceUrl()));
		
		return aiPost;
	}

	public String getVoiceFileSize(String filePath) throws IOException {
		
		String realFilePath = "C:\\capstone" + filePath;
		
		Path path = FileSystems.getDefault().getPath(realFilePath);

        // 파일 속성 가져오기
        BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);

        // 파일 크기 가져오기
        double fileSizeInMB = (double) attributes.size() / (1024 * 1024);
        String formattedFileSize = String.format("%.3f", fileSizeInMB);
        return formattedFileSize;
	}
	
	public Page<RequestComment> getRequestComment(long postId, int page) {
		Pageable pageable = PageRequest.of(page, 30);
		
		/*
		 * List<RequestComment> requestCommentList =
		 * requestCommentRepository.findByRequestPost_IdOrderByRegTimeAsc(postId,
		 * pageable);
		 * 
		 * for (RequestComment comment : requestCommentList) {
		 * comment.setUploaderNickname(); comment.setUploaderImg(); }
		 */
		
		Page<RequestComment> requestCommentList = requestCommentRepository.findByRequestPost_IdOrderByRegTimeAsc(postId, pageable);
		
		for (RequestComment comment : requestCommentList) {
			comment.setUploaderNickname();
			comment.setUploaderImg();
		}
		
		return requestCommentList;
	}
	
	public Page<TipComment> getTipComment(long postId, int page) {
		Pageable pageable = PageRequest.of(page, 30);
		
//		List<TipComment> tipCommentList = tipCommentRepository.findByTipPost_IdOrderByRegTimeAsc(postId, pageable);
		
		Page<TipComment> tipCommentList = tipCommentRepository.findByTipPost_IdOrderByRegTimeAsc(postId, pageable);
		
		for (TipComment comment : tipCommentList) {
			comment.setUploaderNickname();
			comment.setUploaderImg();
		}
		
		return tipCommentList;
	}
	
	public Page<AiComment> getVoiceComment(long postId, int page) {
		Pageable pageable = PageRequest.of(page, 30);
		
//		List<AiComment> aiPostCommentList = aiPostCommentRepository.findByAiPost_IdOrderByRegTimeAsc(postId, pageable);
		
		Page<AiComment> aiPostCommentList = aiPostCommentRepository.findByAiPost_IdOrderByRegTimeAsc(postId, pageable);
		
		for (AiComment comment : aiPostCommentList) {
			comment.setUploaderNickname();
			comment.setUploaderImg();
		}
		
		return aiPostCommentList;
	}
	
	public void saveRequestComment(@LoginUser SessionUser user, String comment, long postId) {
		
		Member member = memberRepository.findByLoginId(user.getLoginId()).get();
		RequestPost requestPost = requestPostRepository.findById(postId);
		
		RequestComment requestComment = new RequestComment();
		requestComment.setMember(member);
		requestComment.setRequestPost(requestPost);
		requestComment.setComment(comment.replace("\r\n","<br/>"));
		
		requestCommentRepository.save(requestComment);
		
	}
	
	public void saveTipComment(@LoginUser SessionUser user, String comment, long postId) {
		
		Member member = memberRepository.findByLoginId(user.getLoginId()).get();
		TipPost tipPost = tipPostRepository.findById(postId);
		
		TipComment tipComment = new TipComment();
		tipComment.setMember(member);
		tipComment.setTipPost(tipPost);
		tipComment.setComment(comment.replace("\r\n","<br/>"));
		
		tipCommentRepository.save(tipComment);
		
	}	
	
	public void saveAiPostComment(@LoginUser SessionUser user, String comment, long postId) {
		
		Member member = memberRepository.findByLoginId(user.getLoginId()).get();
		AiPost aiPost = aiPostRepository.findById(postId);
		
		AiComment aiComment = new AiComment();
		aiComment.setMember(member);
		aiComment.setAiPost(aiPost);
		aiComment.setComment(comment.replace("\r\n","<br/>"));
		aiPostCommentRepository.save(aiComment);
		
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
	
	public void updateAiPostView(long postId) {
		
		AiPost aiPost = aiPostRepository.findById(postId);
		
		aiPost.setView(aiPost.getView() + 1);
		
		aiPostRepository.save(aiPost).getView();
	}
	
	public String getOriVoiceFileName(String voiceName) {
		return aiPostRepository.findOriVoiceNameByVoiceName(voiceName);
	}
}
