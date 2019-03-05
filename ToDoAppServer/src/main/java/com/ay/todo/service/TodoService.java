package com.ay.todo.service;

import com.ay.todo.Todo;

import java.util.List;

public interface TodoService {
    Todo InsertTodo(String description);

    void deleteTodo(Todo todo);

    Todo find(Long id);

    List<Todo> findAll();
}
