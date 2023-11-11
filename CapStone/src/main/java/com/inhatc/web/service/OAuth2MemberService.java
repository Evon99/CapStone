package com.inhatc.web.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.inhatc.web.constant.Role;
import com.inhatc.web.entity.Member;
import com.inhatc.web.info.FacebookMemberInfo;
import com.inhatc.web.info.GoogleMemberInfo;
import com.inhatc.web.info.OAuth2MemberInfo;
import com.inhatc.web.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2MemberService extends DefaultOAuth2UserService {
    private final BCryptPasswordEncoder encoder;
    
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    	OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2MemberInfo memberInfo = null;
        System.out.println(userRequest.getClientRegistration().getRegistrationId());
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            memberInfo = new GoogleMemberInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
            memberInfo = new FacebookMemberInfo(oAuth2User.getAttributes());	
        } else {
            System.out.println("로그인 실패");
        }
        String provider = memberInfo.getProvider();
        String providerId = memberInfo.getProviderId();
        String loginId = provider + "_" + providerId; //중복이 발생하지 않도록 provider와 providerId를 조합
        String email = memberInfo.getEmail();
        String pictureUrl = "notexits";
        if(memberInfo.getPicture() != "") {
        	pictureUrl = memberInfo.getPicture();
        } else {
        	// 이미지 설정 작업
        }
        Role role = Role.ROLE_ADMIN; //일반 유저
        System.out.println(oAuth2User.getAttributes());
        Optional<Member> findMember = memberRepository.findByEmail(loginId);
        Member member=null;
        if (findMember.isEmpty()) { //찾지 못했다면
            member = Member.builder()
                    .name(oAuth2User.getAttribute("name"))
                    .loginId(loginId)
                    .email(email)
//                    .password(encoder.encode("password"))
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .pictureUrl(pictureUrl).build();
            memberRepository.save(member);
        }
        else{
            member=findMember.get();
        }
        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }
}
