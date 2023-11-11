package com.inhatc.web.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inhatc.web.config.auth.LoginUser;
import com.inhatc.web.dto.SessionUser;
import com.inhatc.web.entity.Member;
import com.inhatc.web.entity.MemberDetail;
import com.inhatc.web.entity.Music;
import com.inhatc.web.repository.LikedMusicRepository;
import com.inhatc.web.repository.MemberDetailRepository;
import com.inhatc.web.repository.MemberRepository;
import com.inhatc.web.service.FollowService;
import com.inhatc.web.service.MusicService;
import com.inhatc.web.service.SearchService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SearchController {

	private final MemberRepository memberRepository;
	
	private final MemberDetailRepository memberDetailRepository;
	
	private final MusicService musicService;
	
	private final SearchService searchService;
	
	private final LikedMusicRepository likedMusicRepository;
	
	private final FollowService followService;
	
	@PostMapping("/search")
	public String search(@RequestParam("search-keyword") String keyword, Model model, @LoginUser SessionUser user, HttpSession session) {
		
		List<Music> searchResults = musicService.searchMusicByKeyword(keyword);
		
		for (Music music : searchResults) {
            music.setUploaderNickname();
        }
		
		model.addAttribute("searchResults", searchResults); // 검색 결과
		
		model.addAttribute("keyword", keyword); // 검색어 
		
		// 유저 검색 결과
		List<MemberDetail> searchMember = searchService.searchMember(keyword);
		
		model.addAttribute("searchMember", searchMember);
		
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
	            
	            
	            
	         // 사용자가 좋아요를 누른 음악 ID 목록을 가져옴
	            Long member_id = memberRepository.findIdByLoginId(user.getLoginId());
	            List<Long> likedMusicIds = likedMusicRepository.findByMember_Id(member_id)
	                    .stream()
	                    .map(likedMusic -> likedMusic.getMusic().getId())
	                    .collect(Collectors.toList());

	            System.out.println("likedMusicIds: " + likedMusicIds);
	            model.addAttribute("likedMusicIds", likedMusicIds);
	            
	            model.addAttribute("followService", followService);
	        } else {
	            // Optional이 비어 있다면 처리할 로직 추가
	        }
	    }
		return "search";
	}
	
	@GetMapping("/filter")
	public String filter(@RequestParam String filter, @RequestParam String keyword, Model model, @LoginUser SessionUser user, HttpSession session) {

		if(filter.equals("people")) {
			List<MemberDetail> searchMember = searchService.searchMember(keyword);
			
			model.addAttribute("searchMember", searchMember);
			
			model.addAttribute("keyword", keyword); // 검색어 
		} else {
			List<Music> searchResults = musicService.searchMusicByFilter(filter, keyword);
			
			for (Music music : searchResults) {
	            music.setUploaderNickname();
	        }
			
			model.addAttribute("searchResults", searchResults); // 검색 결과
			
			model.addAttribute("keyword", keyword); // 검색어 
		}
		
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

	            if (loginMemberDetail.getPictureUrl().isEmpty()) {
	                model.addAttribute("loginPictureUrl", loginMember.getPictureUrl());
//	                System.out.println("url: " + member.getPictureUrl());
	            } else {
	                model.addAttribute("loginPictureUrl", loginMemberDetail.getPictureUrl());
	            }
	            
	            
	            
	         // 사용자가 좋아요를 누른 음악 ID 목록을 가져옴
	            Long member_id = memberRepository.findIdByLoginId(user.getLoginId());
	            List<Long> likedMusicIds = likedMusicRepository.findByMember_Id(member_id)
	                    .stream()
	                    .map(likedMusic -> likedMusic.getMusic().getId())
	                    .collect(Collectors.toList());

	            System.out.println("likedMusicIds: " + likedMusicIds);
	            model.addAttribute("likedMusicIds", likedMusicIds);
	            
	            model.addAttribute("followService", followService);
	        } else {
	            // Optional이 비어 있다면 처리할 로직 추가
	        }
	    }
		return "search";
	}
	
	/*
	 * @GetMapping("/filter")
	 * 
	 * @ResponseBody public ResponseEntity<List<Music>> filter(@RequestParam String
	 * filter, @RequestParam String keyword) { List<Music> searchResults =
	 * musicService.searchMusicByFilter(filter, keyword); // 각 음악에 대한 추가적인 처리 (예:
	 * 닉네임 설정) for (Music music : searchResults) { music.setUploaderNickname(); }
	 * return ResponseEntity.ok(searchResults); }
	 */
}
