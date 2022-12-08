package com.springdemo.marmindemo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springdemo.marmindemo.dto.ResponseDTO;
import com.springdemo.marmindemo.service.TodoService;

@RestController
@RequestMapping("todo")
public class TodoController {
	
	//TodoService service = new TodoService();
	@Autowired
	private TodoService service;
	
	@GetMapping("/test")
	public ResponseEntity<?> testTodo(){
		String str = service.testService();
		List<String> list = new ArrayList<>();
		list.add(str);
		ResponseDTO<String> dto = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.ok().body(dto);
	}
}
