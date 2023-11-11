package com.inhatc.web.component;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.inhatc.web.dto.MemberDto;

@Component
public class UserRequestMapper {
    public MemberDto toDto(OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        return MemberDto.builder()
                .email((String)attributes.get("email"))
                .name((String)attributes.get("name"))
                .pictureUrl((String)attributes.get("picture"))
                .build();
    }

}
