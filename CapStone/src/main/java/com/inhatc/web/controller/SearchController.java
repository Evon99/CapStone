package com.inhatc.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		
		long logoutMemberId = 0; // 비로그인 회원의 아이디
		model.addAttribute("loginMemberId", logoutMemberId); 
		model.addAttribute("followService", followService);
		
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
		
		long logoutMemberId = 0; // 비로그인 회원의 아이디
		model.addAttribute("loginMemberId", logoutMemberId); 
		model.addAttribute("followService", followService);
		
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
	
	@GetMapping("/genre")
	public String genre(@RequestParam String genre, @RequestParam String genreImg, @RequestParam int totalPlayHour, @RequestParam int totalPlayMinutes,  Model model, @LoginUser SessionUser user, HttpSession session) throws UnsupportedAudioFileException, IOException {

			List<Music> genreResults = musicService.getByGenreMusic(genre);
			
			for (Music music : genreResults) {
	            music.setUploaderNickname();
	        }
			
			model.addAttribute("genreResults", genreResults); // 장르 결과
			model.addAttribute("genre", genre);
			model.addAttribute("genreImg", genreImg);
			model.addAttribute("tracks", genreResults.size());
			
//			long totalPlayHours = totalDuration / 3600;
//			long totalPlayMinutes = (totalDuration % 3600) / 60;
			
//			String totalPlayTime = String.format("%d시간 %d분", totalPlayHours, totalPlayMinutes);
			
//			model.addAttribute("totalPlayTime", totalPlayTime);
			
			model.addAttribute("totalPlayHour", totalPlayHour);
			model.addAttribute("totalPlayMinutes", totalPlayMinutes);
			
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
		return "genre";
	}
	
	@GetMapping("/getMusicDuration/{genre}")
	public ResponseEntity<List<String>> getMusicDuration(@PathVariable String genre) {
		List<Music> genreResults = musicService.getByGenreMusic(genre);
		
		List<String> musicUrls = genreResults.stream()
                .map(music -> "C:\\capstone" + music.getMusicUrl())
                .collect(Collectors.toList());

        return ResponseEntity.ok(musicUrls);
	}
	
	@GetMapping("/getMusic/images/music/file/{filename:.+}")
	public ResponseEntity<byte[]> getMusic(@PathVariable String filename) throws IOException {
		 // 파일을 ClassPathResource를 통해 읽어옵니다.
		File file = new File("C:\\capstone\\music\\file", filename);

        // 파일을 byte 배열로 변환합니다.
        byte[] bytes = Files.readAllBytes(file.toPath());

        // HTTP 응답에 파일 데이터를 설정합니다.
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "inline; filename=\"" + filename + "\"")
                .body(bytes);
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
