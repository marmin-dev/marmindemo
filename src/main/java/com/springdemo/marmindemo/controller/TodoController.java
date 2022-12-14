package com.springdemo.marmindemo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springdemo.marmindemo.dto.ResponseDTO;
import com.springdemo.marmindemo.dto.TodoDTO;
import com.springdemo.marmindemo.model.TodoEntity;
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
	@PostMapping
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto){
		try {
			String temporaryUserId = "temporary-user";
			
			//TodoEntity로 변환한다
			TodoEntity entity = TodoDTO.toEntity(dto);
			
			//id를 null로 초기화한다. 생성 당시에는 id가 없어야 하기 떄문에
			entity.setId(null);
			
			//임시 유저 아이디 설정 
			entity.setUserId(temporaryUserId);
			
			//서비스를 이용해 Todo 엔티티를 생성한다
			List<TodoEntity> entities = service.create(entity);
			
			//자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO리스트로 변환한다
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			//변환된 TodoDTO 리스트를 이용하여 
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
			//ResponseDTO를 리턴한다
			return ResponseEntity.ok().body(response);
		}catch(Exception e) {
			//혹시 예외가 나는 경우 dto대신 error에 메시지를 넣어 리턴한다
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	@GetMapping
	public ResponseEntity<?> retrieveTodoList(){
		String temporaryUserId = "temporary-user";
		
		//서비스 메서드의 retrieve 메서드를 사용하여 Todo리스트를 가져온다
		List<TodoEntity> entities = service.retrieve(temporaryUserId);
		
		//자바스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO리스트로 변환한다
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		//변환된 TodoDTO리스트를 이용해 ResponseDTO를 초기화한다
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		//ResponseDTO를 리턴한다
		return ResponseEntity.ok().body(response);
	}
	@PutMapping
	public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto){
		String temporaryUserId = "temporary-user";
		
		//1) dto를 entity로 변환하다
		TodoEntity entity = TodoDTO.toEntity(dto);
		
		//id를 temporaryUserId로 초기화한다
		entity.setUserId(temporaryUserId);
		
		//서비스를 이용해 entity를 업데이트한다
		List<TodoEntity> entities = service.update(entity);
		
		//자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO리스트로 변환한다
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		//변환된 TodoDTO리스트를 이용해 ResponseDTO를 초기화한다
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		//ResponseDTO를 리턴한다
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto){
		try {
			//엔티티를 삭제한다
			String temporaryUserId = "temporary-user";
			
			//TodoEntity로 변환한다
			TodoEntity entity = TodoDTO.toEntity(dto);
			
			//임시유저설정
			entity.setUserId(temporaryUserId);
			
			//서비스를 이용해 entity를 삭제한다
			List<TodoEntity> entities = service.delete(entity);
			
			//자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO리스트로 변환한다
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors
					.toList());
			
			//변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
			//ResponseDTO를 리턴한다
			return ResponseEntity.ok().body(response);
		}catch(Exception e){
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
			
		}
	}
}
