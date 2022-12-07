package com.springdemo.marmindemo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> {
	//HTTP응답으로 사용할 DTO
	private String error;
	private List<T> data;
}
