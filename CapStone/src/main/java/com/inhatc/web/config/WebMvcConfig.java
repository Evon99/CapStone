package com.inhatc.web.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.inhatc.web.config.auth.LoginUserArgumentResolver;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer{
	
	@Value("${uploadPath}")
	String uploadPath;
	
	private final LoginUserArgumentResolver loginUserArgumentResolver;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**")
				.addResourceLocations(uploadPath);
		
		registry.addResourceHandler("/files/**")
        .addResourceLocations(uploadPath); // /files/** 경로에 대한 리소스 핸들러 추가
		
		registry.addResourceHandler("/static/**")
		.addResourceLocations("classpath:/static/");
		
		registry.addResourceHandler("/music/**")
        		.addResourceLocations(uploadPath + "/music/");

	}
	
	@Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver);
    }

}
