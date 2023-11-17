package com.inhatc.web.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inhatc.web.config.auth.LoginUser;
import com.inhatc.web.dto.RequestPostDto;
import com.inhatc.web.dto.SessionUser;
import com.inhatc.web.entity.Member;
import com.inhatc.web.entity.MemberDetail;
import com.inhatc.web.entity.RequestComment;
import com.inhatc.web.entity.RequestPost;
import com.inhatc.web.repository.MemberDetailRepository;
import com.inhatc.web.repository.MemberRepository;
import com.inhatc.web.service.PostService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CommunityController {

	private final MemberRepository memberRepository;
	
	private final MemberDetailRepository memberDetailRepository;
	
	private final PostService postService;
	
	@GetMapping("/requestnoticeboard")
	public String requestNoticeBoard(@RequestParam(value="page", defaultValue="0") int page, Model model, @LoginUser SessionUser user, HttpSession session) {
		
		List<RequestPost> requestPost = postService.getRequestList(page);
		model.addAttribute("post", requestPost);
		
		Page<RequestPost> paging = postService.paging(page);
		model.addAttribute("paging", paging);
		
		if (user != null) {
	        Optional<Member> optionalMember = memberRepository.findByLoginId(user.getLoginId());

	        if (optionalMember.isPresent()) {
	        	// 로그인 유저 정보
	            Member loginMember = optionalMember.get();
	            MemberDetail loginMemberDetail = memberDetailRepository.findByMember_Id(loginMember.getId());
	            
	            
	            // Check if memberDetail is null
	            if (loginMemberDetail == null || loginMemberDetail.getNickname() == null) {
	                // Clear the session
	                session.invalidate();
	                // Redirect to the home page
	                return "redirect:/";
	            }

	            model.addAttribute("session", user);
	            model.addAttribute("loginNickname", loginMemberDetail.getNickname());
	            model.addAttribute("loginMemberId", loginMember.getId());
//	            System.out.println("멤버디테일:" + memberDetail.getPictureUrl());

	         // 로그인유저 프로필 이미지 가져오기
	            if (loginMemberDetail.getPictureUrl().isEmpty()) {
	                model.addAttribute("loginPictureUrl", loginMember.getPictureUrl());
//	                System.out.println("url: " + member.getPictureUrl());
	            } else {
	                model.addAttribute("loginPictureUrl", loginMemberDetail.getPictureUrl());
	            }
	            
	        } else {
	            // Optional이 비어 있다면 처리할 로직 추가
	        }
	    }
		return "requestnoticeboard";
	}
	
	@GetMapping("/private/requestpostwrite")
	public String requestPostWrite(Model model, @LoginUser SessionUser user, HttpSession session) {
		
		if (user != null) {
	        Optional<Member> optionalMember = memberRepository.findByLoginId(user.getLoginId());

	        if (optionalMember.isPresent()) {
	        	// 로그인 유저 정보
	            Member loginMember = optionalMember.get();
	            MemberDetail loginMemberDetail = memberDetailRepository.findByMember_Id(loginMember.getId());
	            
	            
	            // Check if memberDetail is null
	            if (loginMemberDetail == null || loginMemberDetail.getNickname() == null) {
	                // Clear the session
	                session.invalidate();
	                // Redirect to the home page
	                return "redirect:/";
	            }

	            model.addAttribute("session", user);
	            model.addAttribute("loginNickname", loginMemberDetail.getNickname());
	            model.addAttribute("loginMemberId", loginMember.getId());
	            model.addAttribute("requestPostDto", new RequestPostDto());
//	            System.out.println("멤버디테일:" + memberDetail.getPictureUrl());

	         // 로그인유저 프로필 이미지 가져오기
	            if (loginMemberDetail.getPictureUrl().isEmpty()) {
	                model.addAttribute("loginPictureUrl", loginMember.getPictureUrl());
//	                System.out.println("url: " + member.getPictureUrl());
	            } else {
	                model.addAttribute("loginPictureUrl", loginMemberDetail.getPictureUrl());
	            }
	            
	        } else {
	            // Optional이 비어 있다면 처리할 로직 추가
	        }
	    }
		return "requestpostwrite";
	}
	
	
	@PostMapping("/private/requestpostwrite")
	public String requestPostWrite(@RequestParam("title") String title, @RequestParam("content") String content, @LoginUser SessionUser user, @Valid RequestPostDto requestPostDto, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) { 
            return "requestpostwrite";
        }
		
		try {
			  postService.saveRequestPost(user, title, content);
      } catch (Exception e) {
          model.addAttribute("errorMessage", e.getMessage());
          return "requestpostwrite";
      }
		
		List<RequestPost> requestPost = postService.getRequestList(0);
		model.addAttribute("post", requestPost);
		
		Page<RequestPost> paging = postService.paging(0);
		model.addAttribute("paging", paging);
		
		return "requestnoticeboard";
	}
	
	@GetMapping("/requestpost/{postId}")
	public String requestPost(@RequestParam(value="page", defaultValue="0") int page, @PathVariable long postId, Model model, @LoginUser SessionUser user, HttpSession session) {
		
		RequestPost requestPost = postService.getRequestPost(postId);
		
		model.addAttribute("requestPost", requestPost);
		
		List<RequestComment> requestCommentList = postService.getRequestComment(postId, page);
		
		model.addAttribute("comment", requestCommentList);
		
		postService.updateView(postId); // 게시글 조회수 증가
		
		if (user != null) {
	        Optional<Member> optionalMember = memberRepository.findByLoginId(user.getLoginId());

	        if (optionalMember.isPresent()) {
	        	// 로그인 유저 정보
	            Member loginMember = optionalMember.get();
	            MemberDetail loginMemberDetail = memberDetailRepository.findByMember_Id(loginMember.getId());
	            
	            
	            // Check if memberDetail is null
	            if (loginMemberDetail == null || loginMemberDetail.getNickname() == null) {
	                // Clear the session
	                session.invalidate();
	                // Redirect to the home page
	                return "redirect:/";
	            }

	            model.addAttribute("session", user);
	            model.addAttribute("loginNickname", loginMemberDetail.getNickname());
	            model.addAttribute("loginMemberId", loginMember.getId());
//	            System.out.println("멤버디테일:" + memberDetail.getPictureUrl());

	         // 로그인유저 프로필 이미지 가져오기
	            if (loginMemberDetail.getPictureUrl().isEmpty()) {
	                model.addAttribute("loginPictureUrl", loginMember.getPictureUrl());
//	                System.out.println("url: " + member.getPictureUrl());
	            } else {
	                model.addAttribute("loginPictureUrl", loginMemberDetail.getPictureUrl());
	            }
	            
	        } else {
	            // Optional이 비어 있다면 처리할 로직 추가
	        }
	    }
		return "requestpost";
	}
	
	@PostMapping("/private/requestcommentwrite")
	public String requestCommentWrite(@RequestParam("postId") long postId, @RequestParam("comment") String comment, @LoginUser SessionUser user, Model model) {
		
		/*
		 * if(bindingResult.hasErrors()) { return "requestpost"; }
		 */
		
		try {
			  postService.saveRequestComment(user, comment, postId);
	      } catch (Exception e) {
	          model.addAttribute("errorMessage", e.getMessage());
	          return "requestpost";
	      }
		
		//List<RequestPost> requestPost = postService.getRequestList(0);
	//	model.addAttribute("post", requestPost);
		
		RequestPost requestPost = postService.getRequestPost(postId);
		
		model.addAttribute("requestPost", requestPost);
		
		List<RequestComment> requestCommentList = postService.getRequestComment(postId, 0);
		
		model.addAttribute("comment", requestCommentList);
		
		return "requestpost";
	}
	
}
