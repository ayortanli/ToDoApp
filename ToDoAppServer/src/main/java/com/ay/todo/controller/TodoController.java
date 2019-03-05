package com.ay.todo.controller;

import com.ay.todo.Todo;
import com.ay.todo.service.TodoService;

import java.util.List;

public class TodoController {
    private TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    public Todo insertNewTodo(String description) {
        return service.insertTodo(description);
    }

    public Todo findTodo(long id) {
        return service.find(id);
    }

    public List<Todo> findAllTodo() {
        return service.findAll();
    }

    public void deleteTodo(long id) {
        service.deleteTodo(id);
    }

    public Todo updateTodoDescription(Long id, String description) {
        return service.updateTodo(id, description);
    }
}
