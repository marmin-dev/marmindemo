package com.springdemo.marmindemo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springdemo.marmindemo.dto.ResponseDTO;
import com.springdemo.marmindemo.dto.TestRequestBodyDTO;

@RestController
@RequestMapping("test")//파라미터=리소스
public class TestController {
	
	@GetMapping
	public String testController() {
		return "hello world";
	}
	@GetMapping("/testGetMapping")
	public String testControllerWithPath() {
		return "hello hello world world";
	}
	@GetMapping("/{id}")
	public String testControllerWithPathVariables(@PathVariable(required =false) int id) {
		return "hello world" + id;
	}
	@GetMapping("/testRequestParam")
	public String testRequestParamController(@RequestParam(required = false) int id) {
		return "hello" + id;
	}
	@GetMapping("/testRequestBody")
	public String testRequestBodyController(@RequestBody TestRequestBodyDTO dto) {
		return dto.getId() + dto.getMessage();
	}
	@GetMapping("/testResponseBody")
	public ResponseDTO<String> testControllerResponseBody(){
		List<String> list = new ArrayList<>();
		list.add("helloworld i'm response dto");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return response;
	}
	@GetMapping("/testResponseEntity")
	public ResponseEntity<?> testControllerResponseEntity(){
		List<String> list = new ArrayList<>();
		list.add("hello you got 400! congr");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		//return ResponseEntity.ok().body(response)//정상응답
		return ResponseEntity.badRequest().body(response);//httperror
	}
}
