package com.inhatc.web.controller;


import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.inhatc.web.config.auth.LoginUser;
import com.inhatc.web.dto.SessionUser;
import com.inhatc.web.repository.LikedMusicRepository;
import com.inhatc.web.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ValueController {

	private final MemberRepository memberRepository;
	
	private final LikedMusicRepository likedMusicRepository;
	
	@GetMapping("/getLikedMusicIds")
    @ResponseBody
    public List<Long> getLikedMusicIds(@LoginUser SessionUser user) {

		Long member_id = memberRepository.findIdByLoginId(user.getLoginId());
        List<Long> likedMusicIds = likedMusicRepository.findByMember_Id(member_id)
                .stream()
                .map(likedMusic -> likedMusic.getMusic().getId())
                .collect(Collectors.toList());
        
        return likedMusicIds;
    }
	
	@GetMapping("/getExistLikedMusic")
	@ResponseBody
	public Map<String, Object> getExistLikedMusic(@RequestParam Long musicId, @LoginUser SessionUser user){
		Map<String, Object> response = new HashMap<>();
		
		Long member_id = memberRepository.findIdByLoginId(user.getLoginId());
		boolean exist = likedMusicRepository.existsByMusic_IdAndMember_Id(musicId, member_id);
		
		response.put("exist", exist);
		return response;
	}

    
}
