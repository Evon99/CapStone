package com.inhatc.web.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inhatc.web.component.UserRequestMapper;
import com.inhatc.web.dto.MemberDto;
import com.inhatc.web.entity.Token;
import com.inhatc.web.service.TokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final UserRequestMapper userRequestMapper;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
                throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        MemberDto userDto = userRequestMapper.toDto(oAuth2User);
        
        // 최초 로그인이라면 회원가입 처리를 한다.

        Token token = tokenService.generateToken(userDto.getEmail(), "USER");
        log.info("{}", token);

//        writeTokenResponse(response, token);
        
        System.out.println("request:" + request);
        System.out.println("response:" + response);
        try {
        	String targetUrl;
        	targetUrl = UriComponentsBuilder.fromUriString("/profileSetting")
        			.queryParam("token", token.getToken())
        			.build().toUriString();
        	getRedirectStrategy().sendRedirect(request, response, targetUrl);
            // Redirect logic here
            System.out.println("targetUrl:" + targetUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeTokenResponse(HttpServletResponse response, Token token) 
                throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        response.addHeader("Auth", token.getToken());
        response.addHeader("Refresh", token.getRefreshToken());
        response.setContentType("application/json;charset=UTF-8");

        var writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(token));
        writer.flush();
    }
}
