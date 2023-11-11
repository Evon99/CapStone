package com.inhatc.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.inhatc.web.entity.Follow;
import com.inhatc.web.entity.MemberDetail;
import com.inhatc.web.repository.FollowRepository;
import com.inhatc.web.repository.MemberDetailRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService {

	private final MemberService memberService;

	private final MemberDetailRepository memberDetailRepository;
	
	private final FollowRepository followRepository;
	
	public void follow(String fromNickname, String toNickname) { // 팔로우
		Follow follow = new Follow();
	
		MemberDetail fromMemberDetail = memberDetailRepository.findByNickname(fromNickname);
		MemberDetail toMemberDetail = memberDetailRepository.findByNickname(toNickname);
		
		follow.setFromMember(fromMemberDetail.getMember());
		follow.setToMember(toMemberDetail.getMember());
		
		followRepository.save(follow);
	}
	
	public boolean isFollow(long fromMemberId, long toMemberId) {
		if(followRepository.countByFromMemberIdAndToMemberId(fromMemberId, toMemberId) == 0) {
			return false;
		}
		return true;
	}
	
	public void unfollow(long fromMemberId, long toMemberId) {
		followRepository.deleteByFromMember_IdAndToMember_Id(fromMemberId, toMemberId);
	}
	
	public List<Follow> findFollower(long toMemberId) {
		List<Follow> followerList = followRepository.findFollowerListWithMemberDetails(toMemberId);
		
		/*
		 * for (Follow follow : followerList) { String fromMemberNickname =
		 * follow.getFromMemberNickname(); // String fromMemberImgUrl =
		 * follow.getFromMemberNickname();
		 * follow.setFromMemberNickname(fromMemberNickname); //
		 * follow.setFromMemberImgUrl(fromMemberImgUrl); }
		 */
		
		return followerList;
	}
	
	public List<Follow> findFollowing(long fromMemberId) {
		List<Follow> followingList = followRepository.findFollowingListWithMemberDetails(fromMemberId);
		
		/*
		 * for (Follow follow : followingList) { String toMemberNickname =
		 * follow.gettoMemberNickname(); String toMemberImgUrl =
		 * follow.gettoMemberNickname(); follow.setFromMemberNickname(toMemberNickname);
		 * follow.setFromMemberImgUrl(toMemberImgUrl); }
		 */
		
		return followingList;
	}
}
