package com.inhatc.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/api/auth/check")
    public Map<String, Object> checkAuth(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = true;
        
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
        	 isAuthenticated = false;
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("isAuthenticated", isAuthenticated);

        return result;
    }
}
