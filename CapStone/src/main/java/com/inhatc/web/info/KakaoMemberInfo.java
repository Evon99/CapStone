package com.inhatc.web.info;

import java.util.Map;

public class KakaoMemberInfo implements OAuth2MemberInfo {

    private String id;
    private Map<String, Object> kakaoAccount;

    public KakaoMemberInfo(Map<String, Object> attributes, String id ) {
        this.kakaoAccount = attributes;
        this.id = id;
    }

    @Override
    public String getProviderId() {
        return id;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        return String.valueOf(kakaoAccount.get("email"));
    }

    @Override
    public String getName() {
        return null;
    }

	@Override
	public String getPicture() {
		// TODO Auto-generated method stub
		return null;
	}
}
