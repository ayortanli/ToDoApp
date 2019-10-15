package com.ay.todo.repository;

import com.ay.todo.TaskState;
import com.ay.todo.Todo;

import java.util.List;

public interface ToDoRepository {
    Todo insert(Todo todo);

    List<Todo> findAll();

    Todo find(Long id);

    void delete(Long id);

    Todo update(Long id, String title, String description);

    Todo updateState(long id, TaskState state);
    List<Todo> updateState(List<Long> ids, TaskState state);
}
