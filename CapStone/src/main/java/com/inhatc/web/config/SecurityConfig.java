package com.inhatc.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.inhatc.web.service.OAuth2MemberService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final OAuth2MemberService oAuth2MemberService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/private/**").authenticated() //private로 시작하는 uri는 로그인 필수
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") //admin으로 시작하는 uri는 관릴자 계정만 접근 가능
                .anyRequest().permitAll() //나머지 uri는 모든 접근 허용
                .and().oauth2Login()
                .loginPage("/loginForm") //로그인이 필요한데 로그인을 하지 않았다면 이동할 uri 설정
                .defaultSuccessUrl("/") //OAuth 구글 로그인이 성공하면 이동할 uri 설정
                .userInfoEndpoint()//로그인 완료 후 회원 정보 받기
                .userService(oAuth2MemberService).and().and().build(); //
    }
}