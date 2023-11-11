package com.inhatc.web.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.inhatc.web.constant.Role;
import com.inhatc.web.entity.Member;

import lombok.Getter;

@Getter
public class PrincipalDetails implements OAuth2User{
    private Member member;
    private Map<String, Object> attributes;

    public PrincipalDetails(Member member) {
        this.member=member;
    }

    public PrincipalDetails(Member member, Map<String, Object> attributes) {
        this.member=member;
        this.attributes=attributes;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(() -> {
            return member.getRole().name();
        });
//        collect.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return member.getRole();
//            }
//        });
//        for(String role : member.getRole()){
//            collect.add(new SimpleGrantedAuthority(role));
//        }
        return collect;
    	
    
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    
    @Override
    public String getName() {
        return "name";
    }


}