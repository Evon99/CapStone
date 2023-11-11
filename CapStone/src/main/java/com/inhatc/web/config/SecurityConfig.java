package com.inhatc.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.inhatc.web.handler.OAuth2SuccessHandler;
import com.inhatc.web.service.CustomOAuth2UserService;
import com.inhatc.web.service.TokenService;
import com.inhatc.web.util.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
	
//    private final OAuth2MemberService oAuth2MemberService;

	private final CustomOAuth2UserService customOAuth2UserService;
	
	
	private final OAuth2SuccessHandler successHandler;
    private final TokenService tokenService;
    
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http                
        	.cors().and()
            .httpBasic().disable()
            .csrf().disable()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
            .headers().frameOptions().disable()
            .and()
            .formLogin().disable()
//            .rememberMe().disable()
            .authorizeRequests()
                .antMatchers("/private/**").authenticated()
//                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/images/**", "/css/**", "/js/**", "/token/**").permitAll() // 이미지 폴더 및 SVG 확장자 허용
                .anyRequest().permitAll()
            .and()
                .logout()
                	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                	.logoutSuccessUrl("/")
            .and()
                .oauth2Login()
//                	.loginPage("/loginForm")
                	.defaultSuccessUrl("/private/profileSetting")
//                	.successHandler(successHandler)
                	.userInfoEndpoint()
                	.userService(customOAuth2UserService);
        	
        	
        
//        http.addFilterBefore(new JwtAuthFilter(tokenService), UsernamePasswordAuthenticationFilter.class);

//        http.authorizeRequests()
//        		.antMatchers("/oauth2/**").permitAll()
//        		.anyRequest().authenticated();

       
  
        return http.build();
    }
    
    
}
