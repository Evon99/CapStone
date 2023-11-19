package com.inhatc.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.inhatc.web.config.auth.LoginUser;
import com.inhatc.web.dto.MusicDto;
import com.inhatc.web.dto.SessionUser;
import com.inhatc.web.entity.LikedMusic;
import com.inhatc.web.entity.Member;
import com.inhatc.web.entity.MemberDetail;
import com.inhatc.web.entity.Music;
import com.inhatc.web.repository.LikedMusicRepository;
import com.inhatc.web.repository.MemberDetailRepository;
import com.inhatc.web.repository.MemberMusicStorageRepository;
import com.inhatc.web.repository.MemberRepository;
import com.inhatc.web.service.MemberDetailService;
import com.inhatc.web.service.MemberService;
import com.inhatc.web.service.MusicService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MusicController {

	private final HttpSession httpSession;
	
	private final MemberDetailService MemberDetailService;
	
	private final MemberRepository memberRepository;
	
	private final MemberDetailRepository memberDetailRepository;
	
	private final MusicService musicService;
	
	private final MemberService memberService;
	
	private final LikedMusicRepository likedMusicRepository;
	
	private final MemberMusicStorageRepository memberMusicStorageRepository;
	
	@GetMapping(value = "/music/upload/new")
	public String MusicForm(Model model) {
		model.addAttribute("MusicDto", new MusicDto());
		return "music/musicForm";
	}
	
	@GetMapping("/private/musicUpload")
	public String musicUpload(Model model, @LoginUser SessionUser user, HttpSession session) {
		
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
	            model.addAttribute("musicDto", new MusicDto());

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
		
	    return "musicUpload";
	}
	
	@PostMapping("/private/musicUpload")
	public String musicNew(@Valid MusicDto musicDto, BindingResult bindingResult, Model model, @RequestParam("musicImgFile")MultipartFile musicImgFile,
			@RequestParam("musicUploadFile")MultipartFile musicUploadFile) {
		
		if(bindingResult.hasErrors()) { 
            return "musicUpload";
        }
		
		
		try {
//            MemberDetailService.savePictureFile(memberImgFile, musicDto.getNickname());
			  musicService.saveMusic(musicImgFile, musicUploadFile, musicDto.getTitle(), musicDto.getAiSinger(), musicDto.getOriSinger(), musicDto.getGenre());
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "musicUpload";
        }
		
		return "redirect:/";
	}
	
	@PostMapping("/like/{musicId}")
	@ResponseBody
    public Map<String, Object> updateLikeCount(@PathVariable Long musicId, @LoginUser SessionUser user) {
        System.out.println("musicId" + musicId);
		System.out.println(musicId.getClass().getName());
		
		Map<String, Object> response = new HashMap<>();
		
		// 세션에서 현재 사용자 정보 가져오기
        if (user == null) {
            // 로그인되어 있지 않으면 로그인 페이지로 리다이렉트 또는 에러 응답을 보낼 수 있습니다.
        	System.out.println("세션 에러");
        	response.put("sessionError", "sessionError");
            return response;
        }

        // 현재 사용자 정보로 좋아요 여부 체크
        Member member = memberRepository.findMemberByLoginId(user.getLoginId());
        if (hasMemberLikedMusic(member, musicId)) {
            // 이미 좋아요를 눌렀으므로 중복 처리
            System.out.println("이미 좋아요를 눌렀습니다.");
            
            response.put("existLike", "existLike");
            return response;
        }

        // 좋아요를 누른 적이 없다면 업데이트 및 저장 처리
        int updatedLikeCount = musicService.updateLikeCount(musicId);
        saveLikedMusic(member, musicId);
        
        response.put("updatedLikeCount", updatedLikeCount);
        
        Long member_id = memberRepository.findIdByLoginId(user.getLoginId());
        List<Long> likedMusicIds = likedMusicRepository.findByMember_Id(member_id)
                .stream()
                .map(likedMusic -> likedMusic.getMusic().getId())
                .collect(Collectors.toList());
        
        response.put("likedMusicIds", likedMusicIds);
        // 업데이트된 좋아요 수 반환
        return response;
    }
	
	@PostMapping("/addMusic/{musicId}")
	@ResponseBody
	public Map<String, Object> addMusic(@PathVariable Long musicId, @LoginUser SessionUser user){
		
		Map<String, Object> response = new HashMap<>();
		
		 if (user == null) {
	            // 로그인되어 있지 않으면 로그인 페이지로 리다이렉트 또는 에러 응답을 보낼 수 있습니다.
	        	System.out.println("세션 에러");
	        	response.put("sessionError", "sessionError");
	        }

		 Member member = memberRepository.findMemberByLoginId(user.getLoginId());
		 
			/*
			 * if(musicService.hasMemberAddMusic(member, musicId)) { return
			 * ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); }
			 */
		 
		 musicService.updateAddMusic(member, musicId);
		 
		 Long member_id = memberRepository.findIdByLoginId(user.getLoginId());
		 List<Long> addMusicIds = memberMusicStorageRepository.findByMember_Id(member_id)
                 .stream()
                 .map(MemberMusicStorage -> MemberMusicStorage.getMusic().getId())
                 .collect(Collectors.toList());
		 
		 response.put("addMusicIds", addMusicIds);
		 return response;
	}
	
	@PostMapping("/addMusicCancel/{musicId}")
	public ResponseEntity<Integer> addMusicDelete(@PathVariable Long musicId, @LoginUser SessionUser user){
		
		 if (user == null) {
	            // 로그인되어 있지 않으면 로그인 페이지로 리다이렉트 또는 에러 응답을 보낼 수 있습니다.
	        	System.out.println("세션 에러");
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	        }

		 Member member = memberRepository.findMemberByLoginId(user.getLoginId());
		 
		 musicService.deleteAddMusic(member, musicId);
		 
		 return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
	}

    private boolean hasMemberLikedMusic(Member member, Long musicId) {
        return member.getLikedMusics().stream().anyMatch(likedMusic -> likedMusic.getMusic().getId().equals(musicId));
    }

    private void saveLikedMusic(Member member, Long musicId) {
        Music music = musicService.getMusicById(musicId);
        LikedMusic likedMusic = new LikedMusic();
        likedMusic.setMember(member);
        likedMusic.setMusic(music);
        likedMusicRepository.save(likedMusic);
    }
    
  
}
