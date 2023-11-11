package com.inhatc.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inhatc.web.entity.Member;
import com.inhatc.web.entity.MemberDetail;
import com.inhatc.web.repository.MemberDetailRepository;
import com.inhatc.web.repository.MemberRepository;
import com.inhatc.web.service.FollowService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FollowController {

	private final FollowService followService;
	
	private final MemberRepository memberRepository;
	
	private final MemberDetailRepository memberDetailRepository;
	
	@PostMapping("/follow")
	@ResponseBody
	public ResponseEntity<String> followUser(@RequestParam String nickname, @RequestParam String loginNickname) {
		followService.follow(loginNickname, nickname);
		return ResponseEntity.ok("Following");
	}

	@PostMapping("/unfollow")
	@ResponseBody
	public ResponseEntity<String> unfollowUser(@RequestParam String nickname, @RequestParam String loginNickname) {
		
		MemberDetail loginMemberDetail = memberDetailRepository.findByNickname(loginNickname);
		MemberDetail memberDetail = memberDetailRepository.findByNickname(nickname);
		
		Member loginMember = memberRepository.findMemberById(loginMemberDetail.getMember().getId());
		Member member = memberRepository.findMemberById(memberDetail.getMember().getId());
		
		followService.unfollow(loginMember.getId(), member.getId());
	    return ResponseEntity.ok("Unfollowed");
	}
	
	@PostMapping("/toggleFollow")
	@ResponseBody
	public String toggleFollow(@RequestParam String nickname, @RequestParam String loginNickname) {
		
		MemberDetail loginMemberDetail = memberDetailRepository.findByNickname(loginNickname);
		MemberDetail memberDetail = memberDetailRepository.findByNickname(nickname);
		
		Member loginMember = memberRepository.findMemberById(loginMemberDetail.getMember().getId());
		Member member = memberRepository.findMemberById(memberDetail.getMember().getId());
		
		if(followService.isFollow(loginMember.getId(), member.getId())) {
			followService.unfollow(loginMember.getId(), member.getId());
			return "Unfollowed";
		} else {
			followService.follow(loginNickname, nickname);
			return "Followed";
		}
	}
}
