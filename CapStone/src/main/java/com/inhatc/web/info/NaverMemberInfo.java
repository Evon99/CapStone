package com.inhatc.web.info;

import java.util.Map;

public class NaverMemberInfo implements OAuth2MemberInfo {
    private Map<String, Object> attributes;

    public NaverMemberInfo(Map<String, Object> attributes ) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getEmail() {
        return String.valueOf(attributes.get("email"));
    }

    @Override
    public String getName() {
        return String.valueOf(attributes.get("name"));
    }

	@Override
	public String getPicture() {
		// TODO Auto-generated method stub
		return null;
	}
}
