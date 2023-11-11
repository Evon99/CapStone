package com.inhatc.web.dto;

import java.io.Serializable;

import com.inhatc.web.entity.Member;

import lombok.Getter;

@Getter
public class SessionUser implements Serializable{

	private String name;
//	private String nickname;
    private String email;
    private String provider;
    private String providerId;
    private String loginId;
    private String pictureUrl;
    
	public SessionUser(Member member) {
		this.name = member.getName();
//		this.nickname = member.getNickname();
		this.email = member.getEmail();
		this.provider = member.getProvider();
		this.providerId = member.getProviderId();
		this.loginId = member.getLoginId();
		this.pictureUrl = member.getPictureUrl();
	}
    
    
}
