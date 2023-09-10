package com.inhatc.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@RequestMapping("/index")
	public String adfs() {
		return "Hello Capstone";
	}
}
