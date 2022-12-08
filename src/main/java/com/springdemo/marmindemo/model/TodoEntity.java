package com.springdemo.marmindemo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Todo")
public class TodoEntity {
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid",strategy = "uuid")
	@Id
	private String id;	//이 오브젝트의 아이디
	private String userId;// 이 오브젝트를 생성한 유저의 아이디
	private String title; //Todo타이틀 ex 운동하기
	private boolean done; //todo를 완료한경우 True
}
