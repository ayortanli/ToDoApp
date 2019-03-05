package com.ay.todo.service;

import com.ay.todo.Todo;
import com.ay.todo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TodoServiceImpl implements TodoService {

    private ToDoRepository repository;

    @Autowired
    public TodoServiceImpl(ToDoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Todo InsertTodo(String description) {
        Todo todo = new Todo(description);
        todo = repository.insert(todo);
        return todo;
    }

    @Override
    public void deleteTodo(Todo todo) {
        repository.delete(todo.getId());
    }

    @Override
    public Todo find(Long id) {
        return repository.find(id);
    }

    @Override
    public List<Todo> findAll() {
        return repository.findAll();
    }
}
