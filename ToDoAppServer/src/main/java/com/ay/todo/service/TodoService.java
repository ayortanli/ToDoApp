package com.ay.todo.service;

import com.ay.todo.Todo;

import java.util.List;

public interface TodoService {
    Todo insertTodo(String description);

    void deleteTodo(Long id);

    Todo updateTodo(Long id, String description);

    Todo find(Long id);

    List<Todo> findAll();
}
