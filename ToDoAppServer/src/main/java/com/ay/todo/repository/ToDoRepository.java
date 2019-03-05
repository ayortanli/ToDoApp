package com.ay.todo.repository;

import com.ay.todo.Todo;

import java.util.List;

public interface ToDoRepository {
    Todo insert(Todo todo);

    List<Todo> findAll();

    Todo find(Long id);

    Todo update(Todo todo);

    void delete(Long id);
}
