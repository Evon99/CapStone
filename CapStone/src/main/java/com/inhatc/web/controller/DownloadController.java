package com.inhatc.web.controller;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import com.inhatc.web.repository.AiPostRepository;
import com.inhatc.web.service.PostService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DownloadController {

	private final PostService postService;
	
	@GetMapping("/voiceFileDownload")
	public ResponseEntity<Resource> downloadVoiceFile(@RequestParam String filePath, @RequestParam String voiceName){
	  
		System.out.println("filePath: " + filePath);
		System.out.println("oriFileName: " + voiceName);
		//Body
	    UrlResource resource;
	    try{
	    	Path path = Paths.get("C:\\capstone" + filePath);

	        resource = new UrlResource(path.toUri());
	        System.out.println("resource: " + resource);
	    }catch (MalformedURLException e){
	        e.getStackTrace();
	        throw new RuntimeException("the given URL path is not valid");
	    }
	    //Header
	    String newFileName = postService.getOriVoiceFileName(voiceName); // 새로운 파일 이름 설정
	    String encodedNewFileName = UriUtils.encode(newFileName, StandardCharsets.UTF_8);

	    String contentDisposition = "attachment; filename=\"" + encodedNewFileName + "\"";

	    return ResponseEntity
	            .ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition)
	            .body(resource);
	}
}
