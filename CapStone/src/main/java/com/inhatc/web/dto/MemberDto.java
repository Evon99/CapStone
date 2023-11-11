package com.inhatc.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberDto {
	private String email;
    private String name;
    private String pictureUrl;

    @Builder
    public MemberDto(String email, String name, String pictureUrl) {
        this.email = email;
        this.name = name;
        this.pictureUrl = pictureUrl;
    }
}
