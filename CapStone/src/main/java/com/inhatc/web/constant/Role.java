package com.inhatc.web.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    ROLE_ADMIN("ADMIN", "관리자"), ROLE_USER("USER", "유저");

    private final String key;
    private final String title;

}
