package com.inhatc.web.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.inhatc.web.config.auth.LoginUser;
import com.inhatc.web.dto.SessionUser;
import com.inhatc.web.entity.MemberMusicStorage;
import com.inhatc.web.repository.LikedMusicRepository;
import com.inhatc.web.repository.MemberMusicStorageRepository;
import com.inhatc.web.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ValueController {

	private final MemberRepository memberRepository;
	
	private final LikedMusicRepository likedMusicRepository;
	
	private final MemberMusicStorageRepository memberMusicStorageRepository;
	
	@GetMapping("/getLikedMusicIds")
    @ResponseBody
    public Map<String, Object> getLikedMusicIds(@LoginUser SessionUser user) {

		Map<String, Object> response = new HashMap<>();
		
		if (user == null) {
			response.put("logoutStatus", "logoutStatus");
			return response;
		}
		Long member_id = memberRepository.findIdByLoginId(user.getLoginId());
        List<Long> likedMusicIds = likedMusicRepository.findByMember_Id(member_id)
                .stream()
                .map(likedMusic -> likedMusic.getMusic().getId())
                .collect(Collectors.toList());
        
        response.put("likedMusicIds", likedMusicIds);
        
        List<Long> addMusicIds = memberMusicStorageRepository.findByMember_Id(member_id)
                .stream()
                .map(MemberMusicStorage -> MemberMusicStorage.getMusic().getId())
                .collect(Collectors.toList());
        
        response.put("addMusicIds", addMusicIds);
        
        return response;
    }
	
	@GetMapping("/getExistLikedAndAddMusic")
	@ResponseBody
	public Map<String, Object> getExistLikedMusic(@RequestParam Long musicId, @LoginUser SessionUser user){
		Map<String, Object> response = new HashMap<>();
		
		Long member_id = memberRepository.findIdByLoginId(user.getLoginId());
		boolean likedExist = likedMusicRepository.existsByMusic_IdAndMember_Id(musicId, member_id);
		
		boolean addExist = memberMusicStorageRepository.existsByMusic_IdAndMember_Id(musicId, member_id);
		
		response.put("likedExist", likedExist);
		response.put("addExist", addExist);
		return response;
	}


}
