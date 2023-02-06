package com.springdemo.marmindemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.support.ReflectivePropertyAccessor.OptimalPropertyAccessor;
import org.springframework.stereotype.Service;

import com.springdemo.marmindemo.model.TodoEntity;
import com.springdemo.marmindemo.persistence.TodoRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class TodoService {
	
	@Autowired
	private TodoRepository repository;
	
	public String testService() {
		// TodoEntity 생성
		TodoEntity entity = TodoEntity.builder().title("first posts").build();
		//TodoEntity 저장
		repository.save(entity);
		//TodoEntity검색
		TodoEntity savedEntity = repository.findById(entity.getId()).get();
		return savedEntity.getTitle();
		
	}
	public List<TodoEntity> create(final TodoEntity entity){
		validate(entity);
		
		repository.save(entity);
		
		log.info("Entity Id:{} is saved",entity.getId());
		
		return repository.findByUserId(entity.getUserId());
	}
	private void validate(final TodoEntity entity) { //리팩토링한 메서드
		//Validation
				if(entity == null) {//엔티티가 비었다면
					log.warn("Entity cannot be null");
					throw new RuntimeException("Entity cannot be null");
				}
				if(entity.getUserId() == null) {//엔티티 아이디가 만약 비었다면
					log.warn("Unknown user");
					throw new RuntimeException("Unknown user");
				}
	}
	public List<TodoEntity> retrieve(final String userId){
		return repository.findByUserId(userId);
	}
	public List<TodoEntity> update(final TodoEntity entity){
		//저장할 엔티티가 유효한지 확인한다
		validate(entity);
		//넘겨받은 엔티티를 id를 이용하여 TodoEntity를 가져온다.
		final Optional<TodoEntity> original = repository.findById(entity.getId());
		
		original.ifPresent(todo->{
			//반환될 TodoEntity가 존재하면 값을 새 Entity 의 값으로 덮어씌운다
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			//데이터베이스에 새 값을 저장한다
			repository.save(todo);
		});
		return retrieve(entity.getUserId());
	} 
	public List<TodoEntity> delete(final TodoEntity entity){
		//저장할 엔티티가 유효한지 확인한다
		validate(entity);
		try {
			//엔티티를 삭제한다
			repository.delete(entity);
		}catch(Exception e) {
			//exception 발생 시 id와 exception을 로깅한다
			log.error("error deleting entity",entity.getId(),e);
			
			//컨트롤러로 exception을 날린다. 데이터베이스 내부 로직을 캡슐화하기 위해 e를 리턴하지 않고 새 exception
			//으로 오브젝트를 리턴한다
			throw new RuntimeException("error deleting entity"+entity.getId());
		}
		return retrieve(entity.getUserId());
	}
}
