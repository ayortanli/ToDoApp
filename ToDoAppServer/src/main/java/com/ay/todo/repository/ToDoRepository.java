package com.ay.todo.repository;

import com.ay.todo.Todo;
import org.springframework.data.repository.CrudRepository;


public interface ToDoRepository extends CrudRepository<Todo, Long> {

}
