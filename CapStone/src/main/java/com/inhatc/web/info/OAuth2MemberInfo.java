package com.inhatc.web.info;

import java.util.Map;

public interface OAuth2MemberInfo {

	
	String getProviderId(); //공급자 아이디 ex) google, facebook
    String getProvider(); //공급자 ex) google, facebook
    String getName(); //사용자 이름 ex) 홍길동 
    String getEmail(); //사용자 이메일 ex) gildong@gmail.com
    String getPicture(); // 사용자 프로필 사진

}









