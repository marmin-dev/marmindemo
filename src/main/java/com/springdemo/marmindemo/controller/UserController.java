package com.springdemo.marmindemo.controller;

import com.springdemo.marmindemo.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springdemo.marmindemo.dto.ResponseDTO;
import com.springdemo.marmindemo.dto.UserDTO;
import com.springdemo.marmindemo.model.UserEntity;
import com.springdemo.marmindemo.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private TokenProvider tokenProvider;


	//bean으로 작성해도 됨
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody UserDTO userDto){
		try {
			if(userDto == null || userDto.getPassword()==null) {
				throw new RuntimeException("Invalid Password value");
			}
			// 요청을 이용해 저장할 유저 만들기
			UserEntity user = UserEntity.builder()
					.username(userDto.getUsername())
					.password(passwordEncoder.encode(userDto.getPassword()))
					.build();
			//서비스를 이용해 리포지터리에 유저 저장
			UserEntity registeredUser = userService.create(user);
			UserDTO responseUserDto = UserDTO.builder()
					.id(registeredUser.getId())
					.username(registeredUser.getUsername())
					.build();
			return ResponseEntity.ok().body(responseUserDto);
		}catch (Exception e) {
			//유저 정보는 항상 하나이므로 리스트로 만들어야 하는 DTO를 사용하지 않고 그냥 UserDTO리턴
			ResponseDTO responseDto = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDto);
		}
	}
	@PostMapping("/signin")
	public ResponseEntity<?> authenticate(@RequestBody UserDTO userDto){
		UserEntity user = userService.getByCredentials(
				userDto.getUsername(),
				userDto.getPassword(),
				passwordEncoder)
		;
		
		if(user != null) {
			final String token = tokenProvider.create(user);
			final UserDTO responseUserDto = UserDTO.builder()
			.username(user.getUsername())
			.id(user.getId())
			.token(token)
			.build();
			return ResponseEntity.ok().body(responseUserDto);
		}else {
			ResponseDTO responseDTO = ResponseDTO.builder()
					.error("Login failed")
					.build();
			return ResponseEntity
					.badRequest()
					.body(responseDTO);
		}
				
	}
}
