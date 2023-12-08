package com.inhatc.web.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inhatc.web.entity.Follow;
import com.inhatc.web.entity.MemberDetail;
import com.inhatc.web.repository.FollowRepository;
import com.inhatc.web.repository.MemberDetailRepository;
import com.inhatc.web.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchService {

	private final MemberRepository memberRepository;
	
	private final MemberDetailRepository memberDetailRepository;
	
	private final FollowRepository followRepository;
	
	public List<MemberDetail> searchMember(String keyword) {
		 List<MemberDetail> searchMember = (List<MemberDetail>) memberDetailRepository.findByNicknameContaining(keyword);
		 // 검색 결과 멤버마다 팔로워 수 세팅
		 for (MemberDetail memberDetail : searchMember) {
			 	if(memberDetail.getPictureUrl().isEmpty()) {
			 		memberDetail.setPictureUrl(memberDetail.getMember().getPictureUrl());
			 	}
			 	List<Follow> memberFollower = followRepository.findFollowerListWithMemberDetails(memberDetail.getMember().getId());
			 	
			 	int followerCount = memberFollower.size();
			 	memberDetail.setFollowerCount(followerCount);
			 	
			 	memberDetail.setMemberId(memberDetail.getMember().getId());
		 }
		 return searchMember;
	}
	
}
