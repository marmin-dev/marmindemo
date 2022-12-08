package com.springdemo.marmindemo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springdemo.marmindemo.model.TodoEntity;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String>{
	List<TodoEntity> findByUserId(String userId);
}
