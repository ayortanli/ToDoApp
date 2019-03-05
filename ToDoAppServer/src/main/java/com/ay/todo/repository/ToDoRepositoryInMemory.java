package com.ay.todo.repository;

import com.ay.todo.Todo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ToDoRepositoryInMemory implements ToDoRepository {

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
    public Todo update(Todo todo) {
        Todo todoFromRepo = find(todo.getId());
        if(todoFromRepo != null){
            todoFromRepo.setDescription(todo.getDescription());
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
