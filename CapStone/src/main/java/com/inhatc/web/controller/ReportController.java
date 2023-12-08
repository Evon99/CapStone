package com.inhatc.web.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inhatc.web.config.auth.LoginUser;
import com.inhatc.web.dto.SessionUser;
import com.inhatc.web.entity.Member;
import com.inhatc.web.entity.MemberDetail;
import com.inhatc.web.entity.Report;
import com.inhatc.web.entity.RequestPost;
import com.inhatc.web.service.ReportService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReportController {

	private final ReportService reportService;
	
	@PostMapping("/reportMusic")
	@ResponseBody
	public void reportMusic(@RequestParam("id") long musicId, @RequestParam("reportContent") String reportContent, @RequestParam("nickname") String nickname) {
		reportService.saveReport(musicId, reportContent, nickname);
	}
	
	@GetMapping("/reportlist")
	public String reportList(@RequestParam(value="page", defaultValue="0") int page, Model model) {
		
		Page<Report> paging = reportService.getReportList(page, "", "");
		
		model.addAttribute("paging", paging);
		
		return "reportList";
	}
	
	@PostMapping("/reportlist")
	public String reportListSearch(@RequestParam(value="page", defaultValue="0") int page, @RequestParam("filter") String filter, @RequestParam("keyword") String keyword, Model model) {
		
		
		Page<Report> paging = reportService.getReportList(page, filter, keyword);
		
		model.addAttribute("paging", paging);
		
		return "reportList";
	}
	
	@PostMapping("/deleteMusic")
	@ResponseBody
	public void deleteMusic(@RequestParam("musicId") long musicId) {
		reportService.deleteMusic(musicId);
	}
}
