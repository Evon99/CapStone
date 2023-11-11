package com.inhatc.web.dto;

import java.util.HashMap;
import java.util.Map;

import com.inhatc.web.constant.Role;
import com.inhatc.web.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {

	private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
//    private String nickname;
    private String email;
    private String provider; //공급자 (google, facebook ...)
    private String providerId; //공급 아이디
    private String loginId; // 로그인 아이디
    private String pictureUrl; // 유저 프로필 사진
    
    @Builder
	public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, 
			String email, String provider, String providerId, String loginId,
			String pictureUrl) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.name = name;
//		this.nickname = nickname;
		this.email = email;
		this.provider = provider;
		this.providerId = providerId;
		this.loginId = loginId;
		this.pictureUrl = pictureUrl;
	}
    
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
    	// 여기서 네이버와 카카오 등 구분 (ofNaver, ofKakao)
        switch (registrationId) {
            case "google":
                return ofGoogle(userNameAttributeName, attributes);
            case "kakao":
                return ofKakao(userNameAttributeName, attributes);
            case "naver":
                return ofNaver(userNameAttributeName, attributes);

        }
       // TODO: Exception 발생
                return null;
    }
    
    private static OAuthAttributes ofGoogle(String userNameAttributeName,
            Map<String, Object> attributes) {
    	return OAuthAttributes.builder()
				.name((String) attributes.get("name"))
//				.nickname((String) attributes.get("nickname"))
				.email((String) attributes.get("email"))
				.provider("google")
				.providerId((String) attributes.get("sub"))
				.loginId("google_" + (String) attributes.get("sub"))
				.pictureUrl((String) attributes.get("picture"))
				.attributes(attributes)
				.nameAttributeKey(userNameAttributeName)
				.build();
    }
    
    private static OAuthAttributes ofKakao(String userNameAttributeName,
            Map<String, Object> attributes) {
    	Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
			
			return OAuthAttributes.builder()
			.name(String.valueOf(kakaoProfile.get("nickname")))
			.email(String.valueOf(kakaoAccount.get("email")))
			.provider("kakao")
			.providerId(attributes.get("id").toString())
			.loginId("kakao_" + attributes.get("id").toString())
			.pictureUrl(String.valueOf(kakaoProfile.get("profile_image_url")))
			.attributes(attributes)
			.nameAttributeKey(userNameAttributeName)
			.build();
			}
    
    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        // naver는 response에 유저정보가 있다.
        Map<String, Object> response = (Map<String, Object>)attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .provider("naver")
    			.providerId((String) response.get("id"))
    			.loginId("naver_" + (String) response.get("id"))
                .pictureUrl((String) response.get("profile_image"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    
    public Member toEntity() {
        return Member.builder()
                .name(name)
//                .nickname(nickname)
                .email(email)
                .role(Role.ROLE_ADMIN)
                .provider(provider)
                .providerId(providerId)
                .loginId(loginId)
                .pictureUrl(pictureUrl)
                .build();
    }
    
    public Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", nameAttributeKey);
        map.put("key", nameAttributeKey);
        map.put("name", name);
        map.put("email", email);
        map.put("pictureUrl", pictureUrl);
        map.put("provider", provider);
        map.put("providerId", providerId);
        map.put("loginId", loginId);

        return map;
    }
    
}
