package com.ay.todo.repository;

import com.ay.todo.Todo;
import com.ay.todo.controller.TodoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ToDoRepositoryInMemory implements ToDoRepository {

    Logger logger = LoggerFactory.getLogger(ToDoRepositoryInMemory.class);

    private List<Todo> todoList;
    private long sequence;

    public ToDoRepositoryInMemory(){
        todoList = new ArrayList<>();
        sequence = 0;
    }

    @Override
    public Todo insert(Todo todo) {
        todo.setId(sequence);
        ++sequence;
        todoList.add(todo);
        return todo;
    }

    @Override
    public List<Todo> findAll() {
        return todoList;
    }

    @Override
    public Todo find(Long id) {
        for (Todo todo : todoList) {
            if (todo.getId().equals(id))
                return todo;
        }
        return null;
    }

    @Override
    public Todo update(Long id, String description) {
        Todo todoFromRepo = find(id);
        if(todoFromRepo != null){
            todoFromRepo.setDescription(description);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Todo todo = find(id);
        if(todo!= null) {
            todoList.remove(todo);
        }
    }
}
