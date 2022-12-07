package com.springdemo.marmindemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")//파라미터=리소스
public class TestController {
	
	@GetMapping
	public String testController() {
		return "hello world";
	}

}
