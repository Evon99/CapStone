package com.inhatc.web.controller;


import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.inhatc.web.repository.LikedMusicRepository;
import com.inhatc.web.repository.MemberDetailRepository;
import com.inhatc.web.repository.MemberRepository;
import com.inhatc.web.service.MemberDetailService;
import com.inhatc.web.service.MusicService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ImageCheckController {

	private final MemberDetailService MemberDetailService;
	
	@PostMapping("/checkImage")
    @ResponseBody
    public Map<String, Object> checkImage(@RequestParam("file") MultipartFile file) throws IOException {
        Map<String, Object> response = new HashMap<>();

        // 파일이 이미지인지 여부를 확인하는 로직
        boolean isImage = isImage(file);
        if (isImage) {
            // 이미지 파일일 경우에는 Base64로 인코딩하여 클라이언트에 전달
        	System.out.println("이미지 파일입니다.");
            String encodedImage = Base64.getEncoder().encodeToString(file.getBytes());
            response.put("encodedImage", encodedImage);
        } else {
        	// 이미지 파일이 아닌 경우 기본 이미지 전달
            System.out.println("이미지 파일이 아닙니다.");
            // 기본 이미지 경로로 수정 (설정해야 하는 이미지 경로로 변경하세요)
            response.put("defaultImage", "/static/images/profile_image.svg");
        }
        response.put("isImage", isImage);

        return response;
    }
	
	@PostMapping("/updateImage")
    @ResponseBody
    public Map<String, Object> updateImage(@RequestParam("file") MultipartFile file, @RequestParam("memberName") String memberName) throws IOException {
        Map<String, Object> response = new HashMap<>();

        System.out.println("닉네임:" + memberName);
        // 파일이 이미지인지 여부를 확인하는 로직
        boolean isImage = isImage(file);
        if (isImage) {
            // 이미지 파일일 경우에는 Base64로 인코딩하여 클라이언트에 전달
        	System.out.println("이미지 파일입니다.");
            String encodedImage = Base64.getEncoder().encodeToString(file.getBytes());
            response.put("encodedImage", encodedImage);
        } else {
        	// 이미지 파일이 아닌 경우 기본 이미지 전달
            System.out.println("이미지 파일이 아닙니다.");
            // 기본 이미지 경로로 수정 (설정해야 하는 이미지 경로로 변경하세요)
            response.put("defaultImage", "/static/images/profile_image.svg");
            return response;
        }
        response.put("isImage", isImage);

        try {
            MemberDetailService.updatePictureFile(file, memberName);
        } catch (Exception e) {
        }
        
        return response;
    }

    private boolean isImage(MultipartFile file) {
        try {
            // 파일의 매직 넘버를 확인하여 이미지인지 여부 판단
            byte[] fileBytes = file.getBytes();

            if (isJpeg(fileBytes)) {
                return true;
            } else if (isGif(fileBytes)) {
                return true;
            } else if (isPng(fileBytes)) {
                return true;
            } else if (isBmp(fileBytes)) {
                return true;
            }

            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false; // 예외 발생 시 처리
        }
    }

    private boolean isJpeg(byte[] fileBytes) {
        // JPEG 파일의 매직 넘버 확인
        return fileBytes.length >= 2 && fileBytes[0] == (byte) 0xFF && fileBytes[1] == (byte) 0xD8;
    }

    private boolean isGif(byte[] fileBytes) {
        // GIF 파일의 매직 넘버 확인
        return fileBytes.length >= 6 &&
                fileBytes[0] == (byte) 'G' &&
                fileBytes[1] == (byte) 'I' &&
                fileBytes[2] == (byte) 'F' &&
                fileBytes[3] == (byte) '8' &&
                (fileBytes[4] == (byte) '7' || fileBytes[4] == (byte) '9') &&
                fileBytes[5] == (byte) 'a';
    }

    private boolean isPng(byte[] fileBytes) {
        // PNG 파일의 매직 넘버 확인
        return fileBytes.length >= 8 &&
                fileBytes[0] == (byte) 0x89 &&
                fileBytes[1] == (byte) 'P' &&
                fileBytes[2] == (byte) 'N' &&
                fileBytes[3] == (byte) 'G' &&
                fileBytes[4] == (byte) 0x0D &&
                fileBytes[5] == (byte) 0x0A &&
                fileBytes[6] == (byte) 0x1A &&
                fileBytes[7] == (byte) 0x0A;
    }
     
    private boolean isBmp(byte[] fileBytes) {
        // BMP 파일의 매직 넘버 확인
        return fileBytes.length >= 2 &&
                fileBytes[0] == (byte) 'B' &&
                fileBytes[1] == (byte) 'M';
    }


    
}
