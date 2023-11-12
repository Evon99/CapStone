package com.inhatc.web.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.inhatc.web.config.auth.LoginUser;
import com.inhatc.web.dto.SessionUser;
import com.inhatc.web.entity.Follow;
import com.inhatc.web.entity.Member;
import com.inhatc.web.entity.MemberDetail;
import com.inhatc.web.entity.Music;
import com.inhatc.web.repository.LikedMusicRepository;
import com.inhatc.web.repository.MemberDetailRepository;
import com.inhatc.web.repository.MemberMusicStorageRepository;
import com.inhatc.web.repository.MemberRepository;
import com.inhatc.web.service.FollowService;
import com.inhatc.web.service.MemberDetailService;
import com.inhatc.web.service.MyPageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MyPageController {

	private final HttpSession httpSession;
	
	private final MemberDetailService MemberDetailService;
	
	private final MemberRepository memberRepository;
	
	private final MemberDetailRepository memberDetailRepository;
	
	private final MyPageService myPageService;
	
	private final LikedMusicRepository likedMusicRepository;
	
	private final FollowService followService;
	
	private final MemberMusicStorageRepository memberMusicStorageRepository;
	
	@GetMapping("/private/mypage/{nickname}")
	public String mypage(@PathVariable String nickname, Model model, @LoginUser SessionUser user, HttpSession session) {
		
		// 유저의 정보
		MemberDetail memberDetail = memberDetailRepository.findByNickname(nickname);
		Member member = memberRepository.findMemberById(memberDetail.getMember().getId());
		
		// 업로드 음악 정보
		List<Music> uploadMusics = myPageService.getUploadMusic(member.getId());
		int numberOfUpload = uploadMusics.size();
		for (Music music : uploadMusics) {
			music.setUploaderNickname();
		}
		
		model.addAttribute("uploadMusics", uploadMusics);
		model.addAttribute("numberOfUpload", numberOfUpload);
		
		// 보관함 저장 음악 정보
		List<Music> addMusics = myPageService.getAddMusic(member.getId());
		
		model.addAttribute("addMusics", addMusics);
		
		// 팔로워, 팔로잉 리스트
		List<Follow> followerList = followService.findFollower(member.getId());
		List<Follow> followingList = followService.findFollowing(member.getId());

		model.addAttribute("followerList", followerList);
		model.addAttribute("numberOfFollower", followerList.size());
		
		model.addAttribute("followingList", followingList);
		model.addAttribute("numberOfFollowing", followingList.size());
		
		// 유저 프로필 이미지 가져오기
		if (memberDetail.getPictureUrl().isEmpty()) {
			model.addAttribute("pictureUrl", member.getPictureUrl());
//	                System.out.println("url: " + member.getPictureUrl());
		} else {
			model.addAttribute("pictureUrl", memberDetail.getPictureUrl());
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
	            model.addAttribute("nickname", nickname);
	            model.addAttribute("memberId", member.getId());
	            model.addAttribute("loginNickname", loginMemberDetail.getNickname());
	            model.addAttribute("loginMemberId", loginMember.getId());

//	            System.out.println(nickname);
//	            System.out.println(loginMemberDetail.getNickname());
	            
	            
	            
	            //System.out.println("멤버디테일:" + memberDetail.getPictureUrl());

	            // 로그인유저 프로필 이미지 가져오기
	            if (loginMemberDetail.getPictureUrl().isEmpty()) {
	                model.addAttribute("loginPictureUrl", loginMember.getPictureUrl());
//	                System.out.println("url: " + member.getPictureUrl());
	            } else {
	                model.addAttribute("loginPictureUrl", loginMemberDetail.getPictureUrl());
	            }
	            
	            
	            Long member_id = memberRepository.findIdByLoginId(user.getLoginId());
	            List<Long> likedMusicIds = likedMusicRepository.findByMember_Id(member_id)
	                    .stream()
	                    .map(likedMusic -> likedMusic.getMusic().getId())
	                    .collect(Collectors.toList());

	            List<Long> addMusicIds = memberMusicStorageRepository.findByMember_Id(member_id)
	                    .stream()
	                    .map(MemberMusicStorage -> MemberMusicStorage.getMusic().getId())
	                    .collect(Collectors.toList());
	            
	            System.out.println("addMusicIds" + addMusicIds);
//	            System.out.println("likedMusicIds: " + likedMusicIds);
	            model.addAttribute("likedMusicIds", likedMusicIds);
	            
	            model.addAttribute("isFollow", followService.isFollow(loginMember.getId(), member.getId()));
	            model.addAttribute("followService", followService);
	        } else {
	            // Optional이 비어 있다면 처리할 로직 추가
	        }
	    }
		
		return "mypage";
	}
	
	
}
